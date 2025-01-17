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
            <el-form-item label="销售订单" prop="salesorderId" :rules="[
            ]">
              <GridSelect title="选择销售订单" labelName='salesorderCode' labelValue='id' :value="inputForm.salesorderId"
                :limit="1" @valueChanges='selectProductDeliveryChange'
                @getValue='(value) => { inputForm.salesorderId = value }' :columns="[
                  {
                    prop: 'salesorderCode',
                    label: '销售订单编号'
                  },
                  {
                    prop: 'customer.customerName',
                    label: '客户名称'
                  },
                  {
                    prop: 'customer.contacts',
                    label: '联系人'
                  },
                  {
                    prop: 'customer.contactsPhone',
                    label: '联系人电话'
                  }
                ]" :searchs="[
  {
    prop: 'salesorderCode',
    label: '销售订单编号'
  }
]" dataListUrl="/sales/salesorder/salesorder/list" queryEntityUrl="/sales/salesorder/salesorder/queryById">
              </GridSelect>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户名称" prop="salesorderCode" :rules="[
            ]">
              <el-input v-model="inputForm.salesorderCode" placeholder="请填写联系人"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人" prop="contacts" :rules="[
            ]">
              <el-input v-model="inputForm.contacts" placeholder="请填写联系人"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系方式" prop="contactsPhone" :rules="[
            ]">
              <el-input v-model="inputForm.contactsPhone" placeholder="请填写联系方式"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="服务类型" prop="serverType" :rules="[
            ]">
            <el-select v-model="inputForm.serverType" placeholder="请选择"  style="width: 100%;">
                          <el-option
                            v-for="item in $dictUtils.getDictList('cus_server')"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                          </el-option>
                      </el-select>
                    </el-form-item>
         
          </el-col>
          <el-col :span="24">
            <el-form-item label="信息概要" prop="serverNotes" :rules="[
            ]">
              <el-input v-model="inputForm.serverNotes" placeholder="请填写信息概要"></el-input>
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
        contactsPhone: ''
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
        this.inputForm.serverState = '0'
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
    }
  }
}
</script>

  
