<template>
  <div class="leader-container">
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
          <el-input v-model="searchForm.position" placeholder="请输入职务" clearable>
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
      <el-table :data="leaderList" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="position" label="职务" />
        <el-table-column prop="responsibility" label="分管工作" />
        <el-table-column prop="phone" label="电话" />
        <el-table-column label="操作" width="150" v-if="isAdmin">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.row)">
              <el-icon><Edit /></el-icon> 编辑
            </el-button>
            <el-button type="danger" size="small" @click="handleDelete(scope.row.id)">
              <el-icon><Delete /></el-icon> 删除
            </el-button>
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
        <el-form-item label="职务" prop="position">
          <el-input v-model="form.position" placeholder="请输入职务" />
        </el-form-item>
        <el-form-item label="分管工作" prop="responsibility">
          <el-input v-model="form.responsibility" placeholder="请输入分管工作" type="textarea" />
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入电话" />
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
  name: 'Leader',
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
        position: ''
      },
      leaderList: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      dialogTitle: '',
      form: {
        id: '',
        name: '',
        position: '',
        responsibility: '',
        phone: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        position: [
          { required: true, message: '请输入职务', trigger: 'blur' }
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
    this.getLeaderList()
  },
  methods: {
    getLeaderList() {
      this.$axios.get('/leader/page', {
        params: {
          current: this.currentPage,
          size: this.pageSize,
          name: this.searchForm.name,
          position: this.searchForm.position
        }
      })
        .then(response => {
          if (response.data.code === 200) {
            this.leaderList = response.data.data.records
            this.total = response.data.data.total
          }
        })
    },
    handleSearch() {
      this.currentPage = 1
      this.getLeaderList()
    },
    handleSizeChange(size) {
      this.pageSize = size
      this.getLeaderList()
    },
    handleCurrentChange(current) {
      this.currentPage = current
      this.getLeaderList()
    },
    handleAdd() {
      this.dialogTitle = '新增领导'
      this.form = {
        id: '',
        name: '',
        position: '',
        responsibility: '',
        phone: ''
      }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑领导'
      this.form = { ...row }
      this.dialogVisible = true
    },
    handleSubmit() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          if (this.form.id) {
            // 编辑
            this.$axios.put('/leader/update', this.form)
              .then(response => {
                if (response.data.code === 200) {
                  this.$message.success('更新成功')
                  this.dialogVisible = false
                  this.getLeaderList()
                } else {
                  this.$message.error(response.data.msg)
                }
              })
          } else {
            // 新增
            this.$axios.post('/leader/save', this.form)
              .then(response => {
                if (response.data.code === 200) {
                  this.$message.success('新增成功')
                  this.dialogVisible = false
                  this.getLeaderList()
                } else {
                  this.$message.error(response.data.msg)
                }
              })
          }
        }
      })
    },
    handleDelete(id) {
      this.$confirm('确定要删除该领导吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$axios.delete(`/leader/delete/${id}`)
            .then(response => {
              if (response.data.code === 200) {
                this.$message.success('删除成功')
                this.getLeaderList()
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
.leader-container {
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
</style>