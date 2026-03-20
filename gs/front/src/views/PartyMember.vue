<template>
  <div class="party-member-container">
    <el-card class="filter-card">
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input v-model="searchForm.name" placeholder="请输入姓名" clearable>
            <template #append>
              <el-button @click="handleSearch"><el-icon><Search /></el-icon></el-button>
            </template>
          </el-input>
        </el-col>
        <el-col :span="8">
          <el-select v-model="searchForm.branchId" placeholder="请选择支部" clearable>
            <el-option
              v-for="branch in branches"
              :key="branch.id"
              :label="branch.branchName"
              :value="branch.id"
            />
          </el-select>
        </el-col>
        <el-col :span="8" class="text-right">
          <el-button type="primary" @click="handleAdd" v-if="isAdmin">
            <el-icon><Plus /></el-icon> 新增
          </el-button>
        </el-col>
      </el-row>
    </el-card>

    <el-card class="table-card" style="margin-top: 20px;">
      <el-table :data="memberList" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="branchName" label="支部" />
        <el-table-column prop="joinDate" label="入党时间" />
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
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="支部" prop="branchId">
          <el-select v-model="form.branchId" placeholder="请选择支部">
            <el-option
              v-for="branch in branches"
              :key="branch.id"
              :label="branch.branchName"
              :value="branch.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="入党时间" prop="joinDate">
          <el-date-picker
            v-model="form.joinDate"
            type="date"
            placeholder="请选择入党时间"
            style="width: 100%"
          />
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
  name: 'PartyMember',
  components: {
    Search,
    Plus,
    Edit,
    Delete
  },
  data() {
    return {
      searchForm: {
        name: '',
        branchId: ''
      },
      memberList: [],
      branches: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      dialogTitle: '',
      form: {
        id: '',
        name: '',
        branchId: '',
        joinDate: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        branchId: [
          { required: true, message: '请选择支部', trigger: 'change' }
        ],
        joinDate: [
          { required: true, message: '请选择入党时间', trigger: 'change' }
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
    this.getBranches()
    this.getMemberList()
  },
  methods: {
    getBranches() {
      this.$axios.get('/party-branch/list')
        .then(response => {
          if (response.data.code === 200) {
            this.branches = response.data.data
          }
        })
    },
    getMemberList() {
      this.$axios.get('/party-member/page', {
        params: {
          current: this.currentPage,
          size: this.pageSize,
          name: this.searchForm.name,
          branchId: this.searchForm.branchId
        }
      })
        .then(response => {
          if (response.data.code === 200) {
            this.memberList = response.data.data.records.map(item => {
              const branch = this.branches.find(b => b.id === item.branchId)
              return {
                ...item,
                branchName: branch ? branch.branchName : ''
              }
            })
            this.total = response.data.data.total
          }
        })
    },
    handleSearch() {
      this.currentPage = 1
      this.getMemberList()
    },
    handleSizeChange(size) {
      this.pageSize = size
      this.getMemberList()
    },
    handleCurrentChange(current) {
      this.currentPage = current
      this.getMemberList()
    },
    handleAdd() {
      this.dialogTitle = '新增党员'
      this.form = {
        id: '',
        name: '',
        branchId: '',
        joinDate: ''
      }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑党员'
      this.form = { ...row }
      this.dialogVisible = true
    },
    handleSubmit() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          if (this.form.id) {
            // 编辑
            this.$axios.put('/party-member/update', this.form)
              .then(response => {
                if (response.data.code === 200) {
                  this.$message.success('更新成功')
                  this.dialogVisible = false
                  this.getMemberList()
                } else {
                  this.$message.error(response.data.msg)
                }
              })
          } else {
            // 新增
            this.$axios.post('/party-member/save', this.form)
              .then(response => {
                if (response.data.code === 200) {
                  this.$message.success('新增成功')
                  this.dialogVisible = false
                  this.getMemberList()
                } else {
                  this.$message.error(response.data.msg)
                }
              })
          }
        }
      })
    },
    handleDelete(id) {
      this.$confirm('确定要删除该党员吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$axios.delete(`/party-member/delete/${id}`)
            .then(response => {
              if (response.data.code === 200) {
                this.$message.success('删除成功')
                this.getMemberList()
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
.party-member-container {
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