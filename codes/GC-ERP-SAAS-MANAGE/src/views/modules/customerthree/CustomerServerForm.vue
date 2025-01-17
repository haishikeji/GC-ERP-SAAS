<template>
  <div>
    <el-dialog :title="title" :close-on-click-modal="false" v-dialogDrag :visible.sync="visible">
      <el-form :model="inputForm" size="small" ref="inputForm" v-loading="loading"
        :class="method === 'view' ? 'readonly' : ''" :disabled="method === 'view'" label-width="120px">
        <el-row :gutter="15">
          <!-- <el-col :span="12">
            <el-form-item label="销售订单id" prop="salesorderId"
                :rules="[
                 ]">
                <el-select v-model="inputForm.salesorderId" placeholder="请选择"  style="width: 100%;">
                          <el-option
                            v-for="item in $dictUtils.getDictList('')"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                          </el-option>
                      </el-select>
           </el-form-item>
        </el-col> -->
          <el-col :span="12">
            <el-form-item label="服务配分人员" prop="serverPersonnel" :rules="[
            ]">
              <GridSelect title="服务配分人员" labelName='comName' labelValue='id' :value="inputForm.serverPersonnel" :limit="1"
                 @getValue='(value) => { inputForm.serverPersonnel = value }'
                :columns="[
                  {
                    prop: 'comName',
                    label: '名称'
                  },
                  {
                    prop: 'comMailbox',
                    label: '邮箱'
                  },
                  {
                    prop: 'comPhone',
                    label: '手机号'
                  }

                ]" :searchs="[
  {
    prop: 'comName',
    label: '名称'
  }
]" dataListUrl="/sales/commissioner/list" queryEntityUrl="/sales/commissioner/queryById">
              </GridSelect>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label-width="0">
              <el-tabs v-model="customerServerTab">
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
import CustomerServerService from '@/api/customer/CustomerServerService'
import GridSelect from '@/components/gridSelect'
export default {
  data() {
    return {
      title: '',
      method: '',
      visible: false,
      loading: false,
      customerServerTab: '0',
      inputForm: {
        id: '',
        salesorderId: '',
        customerId: '',
        serverType: '',
        serverNotes: '',
        serverState: '',
        salesorderCode: '',
        contacts: '',
        contactsPhone: '',
        serverPersonnel: '',
      },

    }
  },
  components: {
    GridSelect
  },
  customerServerService: null,
  created() {
    this.customerServerService = new CustomerServerService()
  },
  methods: {
    init(method, id) {
      this.method = method
      if (method === 'add') {
        this.title = `新建`
        this.inputForm.serverState = '1'
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
        this.customerServerTab = '0'
        if (method === 'edit' || method === 'view') { // 修改或者查看
          this.loading = true
          this.customerServerService.queryById(this.inputForm.id).then(({ data }) => {
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
          this.customerServerService.save(this.inputForm).then(({ data }) => {
            this.visible = false
            this.$message.success(data)
            this.$emit('refreshDataList')
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        }
      })
    },
    selectProductDeliveryChange(data, va) {
      this.inputForm.salesorderCode = data[0].customer.customerName
      this.inputForm.contacts = data[0].customer.contacts
      this.inputForm.contactsPhone = data[0].customer.contactsPhone
    },
    selectProductDeliveryChange1(data, va) {
      console.info(data)
      this.inputForm.comName = data[0].comName
    }

  }
}
</script>

  
