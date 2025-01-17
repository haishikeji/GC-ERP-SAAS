<template>
  <div>
    <el-dialog :title="title" :close-on-click-modal="false" v-dialogDrag :visible.sync="visible">
      <el-form :model="inputForm" size="small" ref="inputForm" v-loading="loading"
        :class="method === 'view' ? 'readonly' : ''" :disabled="method === 'view'" label-width="120px">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="物流公司" prop="wuliuId">
              <GridSelect title="物流公司名称" @valueChange='selectCustomerChange' labelName='customName' labelValue='id'
                :value="inputForm.wuliuId" :limit="1" @valueChanges='selectProductDeliveryChange'
                @getValue='(value) => { inputForm.wuliuId = value }' :columns="[
                  {
                    prop: 'customName',
                    label: '物流公司名称'
                  },
                  {
                    prop: 'contactsName',
                    label: '联系人'
                  },
                  {
                    prop: 'contactsPhone',
                    label: '联系人电话'
                  },
                  {
                    prop: 'license',
                    label: '车牌'
                  }
                ]" :searchs="[
  {
    prop: 'customName',
    label: '物流公司名称'
  }
]" dataListUrl="/logistics/custom/logisticsCustom/list" queryEntityUrl="/logistics/custom/logisticsCustom/queryById">
              </GridSelect>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="司机" prop="contactsName" :rules="[
            ]">
              <el-input v-model="inputForm.contactsName" placeholder="请填写司机"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="司机电话" prop="contactsPhone" :rules="[
            ]">
              <el-input v-model="inputForm.contactsPhone" placeholder="请填写司机电话"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="车牌" prop="license" :rules="[
            ]">
              <el-input v-model="inputForm.license" placeholder="车牌"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品出库单" prop="deliveryOrderNo" :rules="[
            ]">
              <GridSelect title="选择产品出库单" @valueChange='selectCustomerChange1' labelName='deliveryOrderNo' labelValue='id'
                :value="inputForm.shippingId" :limit="1" @getValue='(value) => { inputForm.shippingId = value }' :columns="[
                  {
                    prop: 'deliveryOrderNo',
                    label: '出库单编号'
                  },
                  {
                    prop: 'salesorder.salesorderCode',
                    label: '销售订单'
                  },
                  {
                    prop: 'salesorder.customer.customerName',
                    label: '客户名称'
                  },
                  {
                    prop: 'salesorder.createDate',
                    label: '销售日期'
                  }
                ]" :searchs="[
  {
    prop: 'deliveryOrderNo',
    label: '出库单编号'
  },
  {
    prop: 'salesorder.id',
    label: '销售订单'
  }, {
    prop: 'issueType',
    label: '出库类型',
    value: '1',
    show: 1
  }
]" dataListUrl="/wh/productdelivery/productdelivery/list"
                queryEntityUrl="/wh/productdelivery/productdelivery/queryById">
              </GridSelect>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="发货客户" prop="sendId" :rules="[
            ]">
              <el-input v-model="inputForm.sendId" placeholder="请填写发货客户"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="收货客户" prop="deliveryId" :rules="[
            ]">
              <el-input v-model="inputForm.deliveryId" placeholder="请填写收货客户"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="路线出发地" prop="destination" :rules="[
            ]">
              <el-input v-model="inputForm.destination" placeholder="请填写路线出发地"></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="12">
            <el-form-item label="路线目的地" prop="departure" :rules="[
            ]">
              <el-input v-model="inputForm.departure" placeholder="请填写路线目的地"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="中转地" prop="transfer" :rules="[
            ]">
              <el-input v-model="inputForm.transfer" placeholder="请填写中转地"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="总里程" prop="mileage" :rules="[
            ]">
              <el-input v-model="inputForm.mileage" placeholder="请填写总里程"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所需时间" prop="totalTime" :rules="[
            ]">
              <el-input v-model="inputForm.totalTime" placeholder="请填写所需时间"></el-input>
            </el-form-item>
          </el-col>
          <!-- 
          <el-col :span="12">
            <el-form-item label="发货客户" prop="sendId">
              <GridSelect title="发货客户名称" labelName='customerName' labelValue='id' :value="inputForm.sendId" :limit="1"
                @getValue='(value) => { inputForm.sendId = value }' :columns="[
                  {
                    prop: 'customerName',
                    label: '发货客户名称'
                  },
                  {
                    prop: 'contacts',
                    label: '发货客户联系人'
                  },
                  {
                    prop: 'contactsPhone',
                    label: '发货客户电话'
                  },
                  {
                    prop: 'contactsAddr',
                    label: '发货客户地址'
                  }
                ]" :searchs="[
  {
    prop: 'customerName',
    label: '发货客户名称'
  }
]" dataListUrl="/basic/customer/customer/list" queryEntityUrl="/basic/customer/customer/queryById">
              </GridSelect>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="收货客户" prop="deliveryId">
              <GridSelect title="收货客户名称" labelName='customerName' labelValue='id' :value="inputForm.deliveryId" :limit="1"
                @getValue='(value) => { inputForm.deliveryId = value }' :columns="[
                  {
                    prop: 'customerName',
                    label: '发货客户名称'
                  },
                  {
                    prop: 'contacts',
                    label: '发货客户联系人'
                  },
                  {
                    prop: 'contactsPhone',
                    label: '发货客户电话'
                  },
                  {
                    prop: 'contactsAddr',
                    label: '发货客户地址'
                  }
                ]" :searchs="[
  {
    prop: 'customerName',
    label: '发货客户名称'
  }
]" dataListUrl="/basic/customer/customer/list" queryEntityUrl="/basic/customer/customer/queryById">
              </GridSelect>
            </el-form-item>
          </el-col> -->
          <el-col :span="12">
            <el-form-item label="实际交货日期" prop="realityDate" :rules="[
              { required: true, message: '实际交货日期不能为空', trigger: 'blur' }
            ]">
              <el-date-picker style="width: 100%;" v-model="inputForm.realityDate" type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <!-- 
          <el-col :span="12">
            <el-form-item label="发货日期" prop="sendTime" >
              <el-date-picker style="width: 100%;" v-model="inputForm.sendTime" type="datetime" :picker-options="optionsDisable"
                value-format="yyyy-MM-dd HH:mm:ss" >
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="完成日期" prop="expectDate" >
              <el-date-picker style="width: 100%;" v-model="inputForm.expectDate" type="datetime" :picker-options="optionsDisable"
                value-format="yyyy-MM-dd HH:mm:ss" >
              </el-date-picker>
            </el-form-item>
          </el-col> -->
          <el-col :span="12">
            <el-form-item label="备注" prop="notes" :rules="[
            ]">
              <el-input v-model="inputForm.notes" placeholder="请填写备注"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label-width="0">
              <el-tabs v-model="logisticsTab">
              </el-tabs>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label-width="0">
          <el-tabs>
            <el-tab-pane label="物流清单详情">
              <el-table size="small" :data="inputForm.productdeliverydetailsDTOS" style="width: 100%">
                <el-table-column prop="inventory.inventoryName" label="产品">
                </el-table-column>
                <el-table-column prop="inventory.singalUnit" label="计量单位">
                  <template slot-scope="scope">
                    {{ $dictUtils.getDictLabel("sx_metering_unit", scope.row.inventory.singalUnit, '-') }}
                  </template>
                </el-table-column>
                <el-table-column prop="outboundQuantity" show-overflow-tooltip label="当前出库数量">
                </el-table-column>
                <el-table-column prop="exWarehouse.name" label="出库仓库">
                </el-table-column>
                <el-table-column prop="remark" show-overflow-tooltip label="备注">
                </el-table-column>
              </el-table>
            </el-tab-pane>
          </el-tabs>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="visible = false">关闭</el-button>
        <el-button size="small" type="primary" v-if="method != 'view'" @click="doSubmit()" v-noMoreClick>确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import LogisticsService from '@/api/logistics/order/LogisticsService'
import GridSelect from '@/components/gridSelect'
export default {
  data() {
    return {
      title: '',
      method: '',
      visible: false,
      loading: false,
      logisticsTab: '0',
      inputForm: {
        id: '',
        state: '',
        sendId: '',
        deliveryId: '',
        sendTime: '',
        expectDate: '',
        realityDate: '',
        notes: '',
        wuliuId: '',
        shippingId: '',
        contactsName: '',
        contactsPhone: '',
        license: '',
        productdeliverydetailsDTOS: [],
        destination:'',
        departure:'',
        transfer:'',
        mileage:'',
        totalTime:''
      },
      customer: {
        id: '',
        customerName: '',
        contacts: '',
        contactsPhone: '',
        contactsAddr: '',
        orderAttr: {
          id: ''
        },
      }
    }
  },
  components: {
    GridSelect
  },
  logisticsService: null,
  created() {
    this.logisticsService = new LogisticsService()
  },
  methods: {
    selectCustomerChange(data) {
      this.inputForm.customerName = data.customerName
      this.inputForm.contacts = data.contacts
      this.inputForm.customerId = data.id
      this.inputForm.contactsPhone = data.contactsPhone
      this.inputForm.productdeliverydetailsDTOS = data.contactsAddr
    },
    selectCustomerChange1(data) {
      console.info(data);
      this.inputForm.sendId = data.customerNames
      this.inputForm.deliveryId = data.customerNames
      this.inputForm.destination=data.destination
      this.inputForm.departure=data.departure
      this.inputForm.transfer=data.transfer
      this.inputForm.mileage=data.mileage
      this.inputForm.totalTime=data.totalTime

      this.inputForm.productdeliverydetailsDTOS = data.productdeliverydetailsDTOS
    },
    init(method, id) {
      this.method = method
      if (method === 'add') {
        this.title = `新建订单`
        this.inputForm.id = null
        this.inputForm.productdeliverydetailsDTOS = null
        this.inputForm.deliveryOrderNo = null
        this.inputForm.shippingId = null
      } else if (method === 'edit') {
        this.title = '修改订单'
      } else if (method === 'view') {
        this.title = '查看订单'
      }
      this.visible = true
      this.loading = false
      this.$nextTick(() => {
        this.$refs.inputForm.resetFields()
        this.inputForm.id = id
        this.logisticsTab = '0'
        if (method === 'edit' || method === 'view') { // 修改或者查看
          this.loading = true
          this.logisticsService.queryById(this.inputForm.id).then(({ data }) => {
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
          this.inputForm.state = '0'
          console.info(this.inputForm)
          this.logisticsService.save(this.inputForm).then(({ data }) => {
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
      this.inputForm.contactsName = data[0].contactsName
      this.inputForm.contactsPhone = data[0].contactsPhone
      this.inputForm.license = data[0].license
    }
  }
}
</script>

  
