<template>
  <div>
    <el-dialog :title="title" :close-on-click-modal="false" v-dialogDrag :visible.sync="visible">
      <el-form :model="inputForm" size="small" ref="inputForm" v-loading="loading"
        :class="method==='view'?'readonly':''" :disabled="method==='view'" label-width="120px">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="合同编号" prop="contractCode">
              <el-input v-model="inputForm.contractCode" placeholder="自动生成" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户" prop="customer.id" :rules="[
                  {required: true, message:'客户不能为空', trigger:'blur'}
                 ]">
              <GridSelect title="选择客户" labelName='customerName' labelValue='id' :value="inputForm.customer.id"
                :limit="1" @getValue='(value) => {inputForm.customer.id=value}' :columns="[
            {
              prop: 'customerName',
              label: '客户名称'
            },
            {
              prop: 'contacts',
              label: '联系人'
            },
            {
              prop: 'contactsPhone',
              label: '联系人电话'
            },
            {
              prop: 'contactsAddr',
              label: '联系人地址'
            }
            ]" :searchs="[
            {
              prop: 'customerName',
              label: '客户名称'
            },
            {
              prop: 'contacts',
              label: '联系人'
            },
            {
              prop: 'contactsPhone',
              label: '联系人电话'
            }
            ]" dataListUrl="/basic/customer/customer/list" queryEntityUrl="/basic/customer/customer/queryById">
              </GridSelect>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="合同日期" prop="contractTime" :rules="[
                  {required: true, message:'合同日期不能为空', trigger:'blur'}
                 ]">
              <el-date-picker style="width: 100%;" v-model="inputForm.contractTime" type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>

         <!-- <el-col :span="12">
            <el-form-item label="流程实例ID" prop="procInsId" :rules="[
                 ]">
              <el-input v-model="inputForm.procInsId" placeholder="请填写流程实例ID"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="审核结果" prop="result" :rules="[
                 ]">
              <el-input v-model="inputForm.result" placeholder="请填写审核结果"></el-input>
            </el-form-item>
          </el-col> -->
          <el-col :span="12">
            <el-form-item label="备注" prop="remark" :rules="[
                 ]">
              <el-input v-model="inputForm.remark" placeholder="请填写备注"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="附件" prop="files">
               <el-upload ref="files"
                     :action="`${this.$http.BASE_URL}/sys/file/webupload/upload?uploadPath=/sales/contract`"
                     :headers="{token: $cookie.get('token')}"
                     :on-preview="(file, fileList) => {$window.location.href = file.url}"
                     :on-success="(response, file, fileList) => {
                        inputForm.files = fileList.map(item => item.url || item.response.url).join('|')
                     }"
                     :on-error="(response) => {
                       $message.error(response.message)
                     }"
                     :on-remove="(file, fileList) => {
                       $http.delete(`/sys/file/webupload/deleteByUrl?url=${file.url}`).then(({data}) => {
                         $message.success(data)
                       })
                       inputForm.files = fileList.map(item => item.url).join('|')
                     }"
                     :before-remove="(file, fileList) => {
                       return $confirm(`确定移除 ${file.name}？`)
                     }"
                     multiple
                     :limit="5"
                     :on-exceed="(files, fileList) =>{
                       $message.warning(`当前限制选择 5 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
                     }"
                     :file-list="filesArra">
                     <el-button size="small" type="primary">点击上传</el-button>
                     <div slot="tip" class="el-upload__tip">添加相关附件</div>
                   </el-upload>
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
  import GridSelect from '@/components/gridSelect'
  import SalescontractService from '@/api/sales/contract/SalescontractService'
  export default {
    data() {
      return {
        title: '',
        method: '',
        filesArra: [],
        visible: false,
        loading: false,
        inputForm: {
          id: '',
          contractCode: '',
          customer: {
            id: ''
          },
          contractTime: '',
          files: '',
          procInsId: '',
          result: '',
          remark: ''
        }
      }
    },
    components: {
      GridSelect
    },
    salescontractService: null,
    created() {
      this.salescontractService = new SalescontractService()
    },
    methods: {
      init(method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建销售合同`
        } else if (method === 'edit') {
          this.title = '修改销售合同'
        } else if (method === 'view') {
          this.title = '查看销售合同'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.$refs.files.clearFiles()
          this.filesArra = []
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.salescontractService.queryById(this.inputForm.id).then(({
              data
            }) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.inputForm.files.split('|').forEach((item) => {
                if (item.trim().length > 0) {
                  this.filesArra.push({name: decodeURIComponent(item.substring(item.lastIndexOf('/') + 1)), url: item})
                }
              })
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
            this.salescontractService.save(this.inputForm).then(({
              data
            }) => {
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
