<template>
  <div class="meeting-container">
    <el-card class="filter-card">
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input v-model="searchForm.title" placeholder="请输入标题" clearable>
            <template #append>
              <el-button @click="handleSearch"><el-icon><Search /></el-icon></el-button>
            </template>
          </el-input>
        </el-col>
        <el-col :span="8">
          <el-select v-model="searchForm.hostId" placeholder="请选择主办人" clearable>
            <el-option
              v-for="person in persons"
              :key="person.id"
              :label="person.name"
              :value="person.id"
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
      <el-table :data="meetingList" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="meetingTime" label="时间" />
        <el-table-column prop="location" label="地点" />
        <el-table-column prop="hostName" label="主办人" />
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
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="时间" prop="meetingTime">
          <el-date-picker
            v-model="form.meetingTime"
            type="datetime"
            placeholder="请选择时间"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="地点" prop="location">
          <el-input v-model="form.location" placeholder="请输入地点" />
        </el-form-item>
        <el-form-item label="主办人" prop="hostId">
          <el-select v-model="form.hostId" placeholder="请选择主办人">
            <el-option
              v-for="person in persons"
              :key="person.id"
              :label="person.name"
              :value="person.id"
            />
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
  name: 'Meeting',
  components: {
    Search,
    Plus,
    Edit,
    Delete
  },
  data() {
    return {
      searchForm: {
        title: '',
        hostId: ''
      },
      meetingList: [],
      persons: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      dialogTitle: '',
      form: {
        id: '',
        title: '',
        meetingTime: '',
        location: '',
        hostId: ''
      },
      rules: {
        title: [
          { required: true, message: '请输入标题', trigger: 'blur' }
        ],
        meetingTime: [
          { required: true, message: '请选择时间', trigger: 'change' }
        ],
        location: [
          { required: true, message: '请输入地点', trigger: 'blur' }
        ],
        hostId: [
          { required: true, message: '请选择主办人', trigger: 'change' }
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
    this.getMeetingList()
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
    getMeetingList() {
      this.$axios.get('/meeting/page', {
        params: {
          current: this.currentPage,
          size: this.pageSize,
          title: this.searchForm.title,
          hostId: this.searchForm.hostId
        }
      })
        .then(response => {
          if (response.data.code === 200) {
            this.meetingList = response.data.data.records.map(item => {
              const person = this.persons.find(p => p.id === item.hostId)
              return {
                ...item,
                hostName: person ? person.name : ''
              }
            })
            this.total = response.data.data.total
          }
        })
    },
    handleSearch() {
      this.currentPage = 1
      this.getMeetingList()
    },
    handleSizeChange(size) {
      this.pageSize = size
      this.getMeetingList()
    },
    handleCurrentChange(current) {
      this.currentPage = current
      this.getMeetingList()
    },
    handleAdd() {
      this.dialogTitle = '新增会议'
      this.form = {
        id: '',
        title: '',
        meetingTime: '',
        location: '',
        hostId: ''
      }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑会议'
      this.form = { ...row }
      this.dialogVisible = true
    },
    handleSubmit() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          if (this.form.id) {
            // 编辑
            this.$axios.put('/meeting/update', this.form)
              .then(response => {
                if (response.data.code === 200) {
                  this.$message.success('更新成功')
                  this.dialogVisible = false
                  this.getMeetingList()
                } else {
                  this.$message.error(response.data.msg)
                }
              })
          } else {
            // 新增
            this.$axios.post('/meeting/save', this.form)
              .then(response => {
                if (response.data.code === 200) {
                  this.$message.success('新增成功')
                  this.dialogVisible = false
                  this.getMeetingList()
                } else {
                  this.$message.error(response.data.msg)
                }
              })
          }
        }
      })
    },
    handleDelete(id) {
      this.$confirm('确定要删除该会议吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$axios.delete(`/meeting/delete/${id}`)
            .then(response => {
              if (response.data.code === 200) {
                this.$message.success('删除成功')
                this.getMeetingList()
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
.meeting-container {
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