<template>
  <div class="time-node-container">
    <el-card class="filter-card">
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input v-model="searchForm.事项" placeholder="请输入事项" clearable>
            <template #append>
              <el-button @click="handleSearch"><el-icon><Search /></el-icon></el-button>
            </template>
          </el-input>
        </el-col>
        <el-col :span="8">
          <el-select v-model="searchForm.type" placeholder="请选择类型" clearable>
            <el-option label="法定节假日" value="法定节假日" />
            <el-option label="工作节点" value="工作节点" />
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
      <el-table :data="nodeList" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="事项" label="事项" />
        <el-table-column prop="monthNum" label="月份" />
        <el-table-column prop="dayNum" label="日期" />
        <el-table-column prop="type" label="类型" />
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
        <el-form-item label="事项" prop="事项">
          <el-input v-model="form.事项" placeholder="请输入事项" />
        </el-form-item>
        <el-form-item label="月份" prop="monthNum">
          <el-input v-model.number="form.monthNum" placeholder="请输入月份" />
        </el-form-item>
        <el-form-item label="日期" prop="dayNum">
          <el-input v-model.number="form.dayNum" placeholder="请输入日期" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型">
            <el-option label="法定节假日" value="法定节假日" />
            <el-option label="工作节点" value="工作节点" />
          </el-select>
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
  name: 'TimeNode',
  components: {
    Search,
    Plus,
    Edit,
    Delete
  },
  data() {
    return {
      searchForm: {
        事项: '',
        type: ''
      },
      nodeList: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      dialogTitle: '',
      form: {
        id: '',
        事项: '',
        monthNum: '',
        dayNum: '',
        type: ''
      },
      rules: {
        事项: [
          { required: true, message: '请输入事项', trigger: 'blur' }
        ],
        monthNum: [
          { required: true, message: '请输入月份', trigger: 'blur' }
        ],
        dayNum: [
          { required: true, message: '请输入日期', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请选择类型', trigger: 'change' }
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
    this.getNodeList()
  },
  methods: {
    getNodeList() {
      this.$axios.get('/time-node/page', {
        params: {
          current: this.currentPage,
          size: this.pageSize,
          事项: this.searchForm.事项,
          type: this.searchForm.type
        }
      })
        .then(response => {
          if (response.data.code === 200) {
            this.nodeList = response.data.data.records
            this.total = response.data.data.total
          }
        })
    },
    handleSearch() {
      this.currentPage = 1
      this.getNodeList()
    },
    handleSizeChange(size) {
      this.pageSize = size
      this.getNodeList()
    },
    handleCurrentChange(current) {
      this.currentPage = current
      this.getNodeList()
    },
    handleAdd() {
      this.dialogTitle = '新增时间节点'
      this.form = {
        id: '',
        事项: '',
        monthNum: '',
        dayNum: '',
        type: ''
      }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑时间节点'
      this.form = { ...row }
      this.dialogVisible = true
    },
    handleSubmit() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          if (this.form.id) {
            // 编辑
            this.$axios.put('/time-node/update', this.form)
              .then(response => {
                if (response.data.code === 200) {
                  this.$message.success('更新成功')
                  this.dialogVisible = false
                  this.getNodeList()
                } else {
                  this.$message.error(response.data.msg)
                }
              })
          } else {
            // 新增
            this.$axios.post('/time-node/save', this.form)
              .then(response => {
                if (response.data.code === 200) {
                  this.$message.success('新增成功')
                  this.dialogVisible = false
                  this.getNodeList()
                } else {
                  this.$message.error(response.data.msg)
                }
              })
          }
        }
      })
    },
    handleDelete(id) {
      this.$confirm('确定要删除该时间节点吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$axios.delete(`/time-node/delete/${id}`)
            .then(response => {
              if (response.data.code === 200) {
                this.$message.success('删除成功')
                this.getNodeList()
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
.time-node-container {
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