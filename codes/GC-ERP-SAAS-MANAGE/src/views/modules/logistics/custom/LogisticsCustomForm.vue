<template>
  <div>
    <el-dialog :title="title" :close-on-click-modal="false" v-dialogDrag :visible.sync="visible">
      <el-form :model="inputForm" size="small" ref="inputForm" v-loading="loading"
        :class="method === 'view' ? 'readonly' : ''" :disabled="method === 'view'" label-width="120px">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="物流名称" prop="customName" :rules="[{ required: true, message: '请填写物流名称', trigger: 'blur' }
            ]">
              <el-input v-model="inputForm.customName" placeholder="请填写物流名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电话" prop="customCall" :rules="[{ required: true, message: '请填写电话', trigger: 'blur' }
              , {
              pattern: /^((0\d{2,3}-\d{7,8}))$/,
              message: '请输入正确的座机号',
            }]">
              <el-input v-model="inputForm.customCall" placeholder="请填写电话"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮编" prop="zip" :rules="[{ required: true, message: '请填写邮编', trigger: 'blur' }
            ]">
              <el-input v-model="inputForm.zip" placeholder="请填写邮编"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="地址" prop="address" :rules="[{ required: true, message: '请填写地址', trigger: 'blur' }
            ]">
              <el-input v-model="inputForm.address" placeholder="请填写地址"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="车牌" prop="address" :rules="[{ required: true, message: '请车牌', trigger: 'blur' }
            ]">
              <el-input v-model="inputForm.license" placeholder="请车牌"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人" prop="contactsName" :rules="[{ required: true, message: '请填写联系人', trigger: 'blur' }
            ]">
              <el-input v-model="inputForm.contactsName" placeholder="请填写联系人"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人手机" prop="contactsPhone" :rules="[{ required: true, message: '请填写联系人手机', trigger: 'blur' }
              , {
              pattern: /^((1[34578]\d{9}))$/,
              message: '请输入正确的手机号码或者座机号',
            }]">
              <el-input v-model="inputForm.contactsPhone" placeholder="请填写联系人手机"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label-width="0">
              <el-tabs v-model="logisticsCustomTab">
              </el-tabs>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="visible = false">关闭</el-button>
        <el-button size="small" type="primary" v-if="method != 'view'" @click="doSubmit()" v-noMoreClick>确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import LogisticsCustomService from '@/api/logistics/custom/LogisticsCustomService'
export default {
  data() {
    return {
      title: '',
      method: '',
      visible: false,
      loading: false,
      logisticsCustomTab: '0',
      inputForm: {
        id: '',
        customName: '',
        customCall: '',
        zip: '',
        address: '',
        contactsName: '',
        contactsPhone: '',
        license: ''
      }
    }
  },
  components: {
  },
  logisticsCustomService: null,
  created() {
    this.logisticsCustomService = new LogisticsCustomService()
  },
  methods: {

    init(method, id) {
      this.method = method
      if (method === 'add') {
        this.title = `新建`
        console.info( this.inputForm.id)
        this.inputForm.id = null
        this.inputForm.license = null
      } else if (method === 'edit') {
        this.title = '修改'
      } else if (method === 'view') {
        this.title = '查看'
      }
      this.visible = true
      this.loading = false
      this.$nextTick(() => {
        this.$refs.inputForm.resetFields()
        this.inputForm.id = id
        this.logisticsCustomTab = '0'
        if (method === 'edit' || method === 'view') { // 修改或者查看
        
          this.loading = true
          this.logisticsCustomService.queryById(this.inputForm.id).then(({ data }) => {
            this.inputForm = this.recover(this.inputForm, data)
            this.loading = false
          })
        }
      })
    },
    // 表单提交
    doSubmit() {
      this.$refs['inputForm'].validate((valid) => {
        if (valid) {
          this.loading = true
          this.logisticsCustomService.save(this.inputForm).then(({ data }) => {
            this.visible = false
            this.$message.success(data)
            this.$emit('refreshDataList')
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

  
