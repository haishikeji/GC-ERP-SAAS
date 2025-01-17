<template>
  <el-dialog :title="title" :close-on-click-modal="false" v-dialogDrag :visible.sync="visible">
    <el-form size="small" :model="inputForm" ref="inputForm" :class="method==='view'?'readonly':''"
      :disabled="method==='view'" @keyup.enter.native="doSubmit()" label-width="120px">
      <el-row :gutter="15">
        <el-col :span="12">
          <el-form-item label="存货" prop="inventory.id" :rules="[
                  {required: true, message:'请选择存货', trigger:'blur'}
                 ]">
            <inventory-select :limit='1' :value="inputForm.inventory.id" :params="{}"
              @getValue='(value) => {inputForm.inventory.id=value}' @changeValue="inventoryNameChange">
            </inventory-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="存货编码" prop="inventoryCode">
            <el-input v-model="inputForm.inventory.inventoryCode" placeholder="" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="规格型号" prop="model">
            <el-input v-model="inputForm.inventory.model" placeholder="" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="主计量单位" prop="singalUnit">
            <el-select id="singalUnit" v-model="inputForm.inventory.singalUnit" placeholder="请选择" :disabled="true"
              ref="singalUnit" style="width: 100%;">
              <el-option v-for="item in $dictUtils.getDictList('sx_metering_unit')" :key="item.value"
                :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="未税单价(元)" prop="notaxPrice">
            <el-input v-model="inputForm.inventory.notaxPrice" placeholder="" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="含税单价(元)" prop="taxincludedPrice">
            <el-input v-model="inputForm.inventory.taxincludedPrice" placeholder="" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="存货属性" prop="model">
            <el-input v-model="inputForm.inventory.nventoryAttributeValue" placeholder="" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="可用库存" prop="inventoryAttribute">
            <el-input v-model="inputForm.inventory.inventoryAttribute" type="number" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="采购数量" prop="purOrderNum" :rules="[
                  {required: true, message:'采购数量不能为空', trigger:'blur'}
                 ]">
            <el-input v-model="inputForm.purOrderNum" placeholder="请填写采购数量" @blur="calculate()" type="number"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="供货时间" prop="supplyDate" :rules="[
                 ]">
            <el-date-picker style="width: 100%;" v-model="inputForm.supplyDate" type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="目前含税单价(元)" prop="unitPrice" :rules="[
                 ]">
            <el-input v-model="inputForm.unitPrice" placeholder="请填写目前含税单价" @blur="calculate1()" type="number"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="总价(含税)(元)" prop="totalPrice" :rules="[
                 ]">
            <el-input v-model="inputForm.totalPrice" placeholder="自动生成" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="采购备注" prop="orderAttribute" :rules="[
                         ]">
            <el-input v-model="inputForm.orderAttribute" placeholder="请填写采购备注"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" v-if="method === 'add'" type="primary" @click="continueDoSubmit()">继续添加</el-button>
      <el-button size="small" @click="visible = false">关闭</el-button>
      <el-button size="small" v-if="method !== 'view'" type="primary" @click="doSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import InventoryService from '@/api/basic/inventorytype/InventoryService'
  import GridSelect from '@/components/gridSelect'
  import OrderAttributeService from '@/api/basic/orderattribute/OrderAttributeService'
  import InventorySelect from '@/components/inventorySelect'
  export default {
    data() {
      return {
        title: '',
        method: '',
        visible: false,
        oldInputForm: '',
        inputForm: {
          id: '',
          inventory: {
            id: '',
            inventoryCode: '',
            inventoryName: '',
            model: '',
            singalUnit: '',
            nventoryAttributeValue: '',
            notaxPrice: '',
            taxincludedPrice: '',
            inventoryAttribute:''
          },
          purOrderNum: '',
          purUsedNum: 0,
          purUnusedNum: '',
          supplyDate: '',
          unitPrice: '',
          totalPrice: '',
          orderAttribute: ''
        },
        attrList: [],
        orderAttributes: []
      }
    },
    components: {
      GridSelect,
      InventorySelect
    },
    inventoryService: null,
    orderAttributeService: null,
    created() {
      this.inventoryService = new InventoryService()
      this.orderAttributeService = new OrderAttributeService()
    },
    methods: {
      init(method, obj) {
        this.method = method
        if (method === 'add') {
          this.title = `新建采购订单详情`
        } else if (method === 'edit') {
          this.title = '修改采购订单详情'
        } else if (method === 'view') {
          this.title = '查看采购订单详情'
        }
        this.visible = true
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.orderAttributes = []
          this.getAttrList()
          this.inputForm.id = ''
          this.inputForm.inventory = ''
          this.oldInputForm = ''
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.oldInputForm = obj
            this.inputForm = JSON.parse(JSON.stringify(obj))
          }
        })
      },
      doSubmit() {
        this.inputForm.purUnusedNum = this.inputForm.purOrderNum
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.$emit('addRow', this.oldInputForm, JSON.parse(JSON.stringify(this.inputForm)))
            this.visible = false
          }
        })
      },
      continueDoSubmit() {
        this.inputForm.purUnusedNum = this.inputForm.purOrderNum
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.$emit('addRow', this.oldInputForm, JSON.parse(JSON.stringify(this.inputForm)))
            this.$refs['inputForm'].resetFields()
          }
        })
      },
      // 计算数据
      calculate() {
        if (this.inputForm.purOrderNum) {
          if (this.inputForm.unitPrice == undefined || this.inputForm.unitPrice.length <= 0) {
            this.inputForm.unitPrice = this.inputForm.inventory.taxincludedPrice
          }
          this.inputForm.totalPrice = parseFloat(this.inputForm.unitPrice * this.inputForm.purOrderNum).toFixed(2)
        }
      },
      // 计算数据
      calculate1() {
        if (this.inputForm.purOrderNum) {
          if (this.inputForm.unitPrice == undefined || this.inputForm.unitPrice.length <= 0) {
            this.inputForm.unitPrice = this.inputForm.inventory.taxincludedPrice
          }
          this.inputForm.totalPrice = parseFloat(this.inputForm.unitPrice * this.inputForm.purOrderNum).toFixed(2)
        }
      },
      inventoryNameChange(data) {
        this.inputForm.inventory = data[0]
      },
      getAttrList() {
        let _this = this
        _this.orderAttributeService.list({}).then(({
          data
        }) => {
          _this.attrList = data.records
        })
      }
    }
  }
</script>
