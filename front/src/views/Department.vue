<template>
  <div class="department-container">
    <el-card class="filter-card">
      <el-row :gutter="20">
        <el-col :span="16">
          <el-input v-model="searchForm.departmentName" placeholder="请输入部门名称" clearable>
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
      <el-table :data="departmentList" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="departmentName" label="部门名称" />
        <el-table-column prop="leaderName" label="负责人" />
        <el-table-column prop="sort" label="排序" />
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
        <el-form-item label="部门名称" prop="departmentName">
          <el-input v-model="form.departmentName" placeholder="请输入部门名称" />
        </el-form-item>
        <el-form-item label="负责人" prop="leaderId">
          <el-select v-model="form.leaderId" placeholder="请选择负责人">
            <el-option
              v-for="person in persons"
              :key="person.id"
              :label="person.name"
              :value="person.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model.number="form.sort" placeholder="请输入排序" />
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
  name: 'Department',
  components: {
    Search,
    Plus,
    Edit,
    Delete
  },
  data() {
    return {
      searchForm: {
        departmentName: ''
      },
      departmentList: [],
      persons: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      dialogTitle: '',
      form: {
        id: '',
        departmentName: '',
        leaderId: '',
        sort: 0
      },
      rules: {
        departmentName: [
          { required: true, message: '请输入部门名称', trigger: 'blur' }
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
    this.getDepartmentList()
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
    getDepartmentList() {
      this.$axios.get('/department/page', {
        params: {
          current: this.currentPage,
          size: this.pageSize,
          departmentName: this.searchForm.departmentName
        }
      })
        .then(response => {
          if (response.data.code === 200) {
            this.departmentList = response.data.data.records.map(item => {
              const person = this.persons.find(p => p.id === item.leaderId)
              return {
                ...item,
                leaderName: person ? person.name : ''
              }
            })
            this.total = response.data.data.total
          }
        })
    },
    handleSearch() {
      this.currentPage = 1
      this.getDepartmentList()
    },
    handleSizeChange(size) {
      this.pageSize = size
      this.getDepartmentList()
    },
    handleCurrentChange(current) {
      this.currentPage = current
      this.getDepartmentList()
    },
    handleAdd() {
      this.dialogTitle = '新增部门'
      this.form = {
        id: '',
        departmentName: '',
        leaderId: '',
        sort: 0
      }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑部门'
      this.form = { ...row }
      this.dialogVisible = true
    },
    handleSubmit() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          if (this.form.id) {
            // 编辑
            this.$axios.put('/department/update', this.form)
              .then(response => {
                if (response.data.code === 200) {
                  this.$message.success('更新成功')
                  this.dialogVisible = false
                  this.getDepartmentList()
                } else {
                  this.$message.error(response.data.msg)
                }
              })
          } else {
            // 新增
            this.$axios.post('/department/save', this.form)
              .then(response => {
                if (response.data.code === 200) {
                  this.$message.success('新增成功')
                  this.dialogVisible = false
                  this.getDepartmentList()
                } else {
                  this.$message.error(response.data.msg)
                }
              })
          }
        }
      })
    },
    handleDelete(id) {
      this.$confirm('确定要删除该部门吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$axios.delete(`/department/delete/${id}`)
            .then(response => {
              if (response.data.code === 200) {
                this.$message.success('删除成功')
                this.getDepartmentList()
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
.department-container {
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