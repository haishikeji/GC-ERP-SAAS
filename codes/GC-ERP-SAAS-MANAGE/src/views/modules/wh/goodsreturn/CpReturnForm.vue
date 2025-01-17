<template>
<div>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible" width="80%">
    <el-form :model="inputForm" size="small" ref="inputForm" v-loading="loading" :class="method==='view'?'readonly':''"  :disabled="method==='view'"
             label-width="120px">
      <el-row  :gutter="15">
      <el-col :span="12">
            <el-form-item label="销售订单" prop="salesorder.id"
                :rules="[]">
          <GridSelect
            title="选择销售订单"
            labelName = 'salesorderCode'
            labelValue = 'id'
            :value = "inputForm.salesorder.id"
            :limit="1"
            @getValue='(value) => {inputForm.salesorder.id=value }'
            @valueChange="salesorderChange"
            :columns="[
            {
              prop: 'salesorderCode',
              label: '销售订单编号'
            },
            {
              prop: 'salesorderBatchnumber',
              label: '销售订单批号'
            },
            {
              prop: 'salescontract.contractCode',
              label: '合同编号'
            },
            {
              prop: 'customer.customerName',
              label: '客户名称'
            },
            {
              prop: 'totalpriceIncludingtax',
              label: '合计含税总价(元)'
            },
            {
              prop: 'totalNumber',
              label: '订单总数量'
            },
            {
              prop: 'salesorderState',
              label: '销售订单状态'
            },
            {
              prop: 'createDate',
              label: '创建时间'
            }
            ]"
            :searchs="[
            {
              prop: 'salesorderCode',
              label: '销售订单编号'
            },
            {
              prop: 'salesorderBatchnumber',
              label: '销售订单批号'
            }
            ]"
            dataListUrl="/sales/salesorder/salesorder/list"
            queryEntityUrl="/sales/salesorder/salesorder/queryById">
          </GridSelect>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="产品" prop="inventory.id"
                :rules="[
                  {required: true, message:'产品不能为空', trigger: ['blur', 'change']}
                 ]">
              <goods-select v-if="inputForm.salesorder.id"   :limit="1"  :value = "inputForm.salesorderDetail.id"
              @getValue='(value) => {inputForm.salesorderDetail.id=value}'
               :salesorderId="inputForm.salesorder.id" @valueChange="salesorderDetailChange">
               </goods-select>

              <inventory-select  v-else :limit='1' :value="inputForm.inventory.id" @getValue='(value) => {inputForm.inventory.id=value}'   @changeValue='changeGoods'></inventory-select>


           </el-form-item>
        </el-col>
         <el-col :span="12">
          <el-form-item label="存货编码" prop="inventory.inventoryCode">
            <el-input v-model="inputForm.inventory.inventoryCode" placeholder="" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="规格型号" prop="inventory.model">
            <el-input v-model="inputForm.inventory.model" placeholder="" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="主计量单位" prop="inventory.singalUnit">
            <el-select id="singalUnit" v-model="inputForm.inventory.singalUnit" placeholder="请选择" :disabled="true"
              ref="singalUnit" style="width: 100%;">
              <el-option v-for="item in $dictUtils.getDictList('sx_metering_unit')" :key="item.value"
                :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="存货属性" prop="inventory.nventoryAttributeValue">
            <el-input v-model="inputForm.inventory.nventoryAttributeValue" placeholder="" :disabled="true"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
            <el-form-item label="退货客户" prop="customer.id"
                :rules="[
                  {required: true, message:'客户不能为空', trigger: ['blur', 'change']}
                 ]">
          <GridSelect
          :disabled="inputForm.salesorder.id!=''"
            title="选择退货客户"
            labelName = 'customerName'
            labelValue = 'id'
            :value = "inputForm.customer.id"
            :limit="1"
            @getValue='(value) => {inputForm.customer.id=value}'
            :columns="[
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
            ]"
            :searchs="[
            {
              prop: 'customerName',
              label: '客户名称'
            },
            {
              prop: 'contacts',
              label: '联系人'
            }
            ]"
            dataListUrl="/basic/customer/customer/list"
            queryEntityUrl="/basic/customer/customer/queryById">
          </GridSelect>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="数量" prop="num"
                :rules="[
                  {validator: this.validator.isIntGtZero, trigger:  ['blur', 'change']},
                  {required: true, message:'数量不能为空', trigger: ['blur', 'change']}
                 ]">
              <el-input v-model="inputForm.num" placeholder="请填写数量"     ></el-input>
           </el-form-item>
        </el-col>

        <el-col :span="12">
            <el-form-item label="退货仓库" prop="returnWarehouse.id"
                :rules="[
                  {required: true, message:'退货仓库不能为空', trigger: ['blur', 'change']}
                 ]">
          <GridSelect
            title="选择退货仓库"
            labelName = 'name'
            labelValue = 'id'
            :value = "inputForm.returnWarehouse.id"
            :limit="1"
            @getValue='(value) => {inputForm.returnWarehouse.id=value}'
            :columns="[
            {
              prop: 'name',
              label: '仓库名称'
            }
            ]"
            :searchs="[
            {
              prop: 'name',
              label: '仓库名称'
            }
            ]"
            dataListUrl="/basic/warehouse/wareHouse/list"
            queryEntityUrl="/basic/warehouse/wareHouse/queryById">
          </GridSelect>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="备注" prop="remark"
                :rules="[]">
                <el-input
                type="textarea"
                v-model="inputForm.remark"
                placeholder="请填写备注"
              ></el-input>

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
  import UserSelect from '@/components/userSelect'
  import CpReturnService from '@/api/wh/goodsreturn/CpReturnService'
import GoodsSelect from './goodsSelect.vue'
import InventorySelect from '@/components/inventorySelect'
import InventoryWarehouseSelect from '@/components/inventoryWarehouseSelect'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        inputForm: {
          id: '',
          inventory: {
            id: '',
            unitOfMeasurement: '',
            inventoryAttribute: '',
            lockStock: '',
            inventoryCode: '',
            model: '',
            singalUnit: '',
            nventoryAttributeValue: '',
            inventoryWarehouse: {
              id: '',
              inventoryInStock: '',
              availableStock: '',
              lockStock: '',
              warehouse: {
                name: ''
              }
            }
          },
          salesorder: {
            id: ''
          },
          salesorderDetail: {
            id: ''
          },
          customer: {
            id: ''
          },
          num: '',
          returnDate: '',
          returnUser: {
            id: ''
          },
          returnWarehouse: {
            id: ''
          },
          remark: ''
        }
      }
    },
    components: {
      GridSelect,
      UserSelect,
      GoodsSelect,
      InventorySelect,
      InventoryWarehouseSelect
    },
    cpReturnService: null,
    created () {
      this.cpReturnService = new CpReturnService()
    },
    methods: {
      salesorderDetailChange(datas) {
        var data = datas[0]
        this.inputForm.inventory = data.inventory
      },
      salesorderChange(data) {
        this.inputForm.customer.id = data.customer.id
        this.inputForm.salesorderDetail.id = ''
        this.inputForm.inventory.id = ''
      },
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建退货管理`
        } else if (method === 'edit') {
          this.title = '修改退货管理'
        } else if (method === 'view') {
          this.title = '查看退货管理'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.cpReturnService.queryById(this.inputForm.id).then(({data}) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.loading = false
            })
          }
        })
      },
      // 表单提交
      doSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.cpReturnService.save(this.inputForm).then(({data}) => {
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
      changeGoods(data) {
        this.inputForm.inventory = data[0]
      }
    }
  }
</script>


