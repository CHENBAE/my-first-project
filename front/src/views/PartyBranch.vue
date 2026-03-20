<template>
  <div class="party-branch-container">
    <el-card class="filter-card">
      <el-row :gutter="20">
        <el-col :span="16">
          <el-input v-model="searchForm.branchName" placeholder="请输入支部名称" clearable>
            <template #append>
              <el-button @click="handleSearch"><el-icon><Search /></el-icon></el-button>
            </template>
          </el-input>
        </el-col>
        <el-col :span="8" class="text-right">
          <el-button type="primary" @click="handleAdd" v-if="isAdmin">
            <el-icon><Plus /></el-icon> 新增
          </el-button>
        </el-col>
      </el-row>
    </el-card>

    <el-card class="table-card" style="margin-top: 20px;">
      <el-table :data="branchList" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="branchName" label="支部名称" />
        <el-table-column prop="secretaryName" label="书记" />
        <el-table-column prop="memberCount" label="党员人数" />
        <el-table-column prop="remark" label="备注" />
        <el-table-column label="操作" width="150" v-if="isAdmin">
          <template #default="scope">
            <div class="action-buttons">
              <el-button type="primary" size="small" @click="handleEdit(scope.row)">
                <el-icon><Edit /></el-icon> 编辑
              </el-button>
              <el-button type="danger" size="small" @click="handleDelete(scope.row.id)">
                <el-icon><Delete /></el-icon> 删除
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination" style="margin-top: 20px;">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 新增/编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="支部名称" prop="branchName">
          <el-input v-model="form.branchName" placeholder="请输入支部名称" />
        </el-form-item>
        <el-form-item label="书记" prop="secretaryId">
          <el-select v-model="form.secretaryId" placeholder="请选择书记">
            <el-option
              v-for="person in persons"
              :key="person.id"
              :label="person.name"
              :value="person.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="党员人数" prop="memberCount">
          <el-input v-model.number="form.memberCount" placeholder="请输入党员人数" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { Search, Plus, Edit, Delete } from '@element-plus/icons-vue'

export default {
  name: 'PartyBranch',
  components: {
    Search,
    Plus,
    Edit,
    Delete
  },
  data() {
    return {
      searchForm: {
        branchName: ''
      },
      branchList: [],
      persons: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      dialogTitle: '',
      form: {
        id: '',
        branchName: '',
        secretaryId: '',
        memberCount: 0,
        remark: ''
      },
      rules: {
        branchName: [
          { required: true, message: '请输入支部名称', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    isAdmin() {
      const user = JSON.parse(localStorage.getItem('user'))
      return user && user.role === 'admin'
    }
  },
  mounted() {
    this.getPersons()
    this.getBranchList()
  },
  methods: {
    getPersons() {
      this.$axios.get('/person/list')
        .then(response => {
          if (response.data.code === 200) {
            this.persons = response.data.data
          }
        })
    },
    getBranchList() {
      this.$axios.get('/party-branch/page', {
        params: {
          current: this.currentPage,
          size: this.pageSize,
          branchName: this.searchForm.branchName
        }
      })
        .then(response => {
          if (response.data.code === 200) {
            this.branchList = response.data.data.records.map(item => {
              const person = this.persons.find(p => p.id === item.secretaryId)
              return {
                ...item,
                secretaryName: person ? person.name : ''
              }
            })
            this.total = response.data.data.total
          }
        })
    },
    handleSearch() {
      this.currentPage = 1
      this.getBranchList()
    },
    handleSizeChange(size) {
      this.pageSize = size
      this.getBranchList()
    },
    handleCurrentChange(current) {
      this.currentPage = current
      this.getBranchList()
    },
    handleAdd() {
      this.dialogTitle = '新增党支部'
      this.form = {
        id: '',
        branchName: '',
        secretaryId: '',
        memberCount: 0,
        remark: ''
      }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑党支部'
      this.form = { ...row }
      this.dialogVisible = true
    },
    handleSubmit() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          if (this.form.id) {
            // 编辑
            this.$axios.put('/party-branch/update', this.form)
              .then(response => {
                if (response.data.code === 200) {
                  this.$message.success('更新成功')
                  this.dialogVisible = false
                  this.getBranchList()
                } else {
                  this.$message.error(response.data.msg)
                }
              })
          } else {
            // 新增
            this.$axios.post('/party-branch/save', this.form)
              .then(response => {
                if (response.data.code === 200) {
                  this.$message.success('新增成功')
                  this.dialogVisible = false
                  this.getBranchList()
                } else {
                  this.$message.error(response.data.msg)
                }
              })
          }
        }
      })
    },
    handleDelete(id) {
      this.$confirm('确定要删除该党支部吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$axios.delete(`/party-branch/delete/${id}`)
            .then(response => {
              if (response.data.code === 200) {
                this.$message.success('删除成功')
                this.getBranchList()
              } else {
                this.$message.error(response.data.msg)
              }
            })
        })
        .catch(() => {})
    }
  }
}
</script>

<style scoped>
.party-branch-container {
  width: 100%;
}

.filter-card {
  margin-bottom: 20px;
}

.table-card {
  margin-top: 20px;
}

.pagination {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

.action-buttons {
  display: flex;
  gap: 10px;
  align-items: center;
}
</style>