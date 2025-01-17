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
          <el-form-item label="计量单位" prop="singalUnit">
            <el-select id="singalUnit" v-model="inputForm.inventory.singalUnit" placeholder="请选择" :disabled="true"
              ref="singalUnit" style="width: 100%;">
              <el-option v-for="item in $dictUtils.getDictList('sx_metering_unit')" :key="item.value"
                :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="存货属性" prop="model">
            <el-input v-model="inputForm.inventory.nventoryAttributeValue" placeholder="" :disabled="true"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="计划生产数量" prop="planProduceNum" :rules="[
                 ]">
            <el-input v-model="inputForm.planProduceNum" placeholder="" type="number"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="订货日期" prop="orderDate" :rules="[
                 ]">
            <el-date-picker style="width: 100%;" v-model="inputForm.orderDate" type="datetime" :disabled="true"
              value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="交货日期" prop="deliveryDate" :rules="[
                 ]">
            <el-date-picker style="width: 100%;" v-model="inputForm.deliveryDate" type="datetime" :disabled="true"
              value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="计划完成日期" prop="planFinishDate" :rules="[
                 ]">
            <el-date-picker style="width: 100%;" v-model="inputForm.planFinishDate" type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="实际完成日期" prop="trueFinishDate" :rules="[
                 ]">
            <el-date-picker style="width: 100%;" v-model="inputForm.trueFinishDate" type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
        </el-col>


        <el-col :span="12" v-for="item in attrList" :key="item.value">
          <el-form-item :label="item.chineseName" :prop="item.englishName" :rules="[
                 ]">
            <el-input v-model="inputForm[item.englishName]" :placeholder="'请填写'+item.chineseName"></el-input>
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
  import InventorySelect from '@/components/inventorySelect'
  import OrderAttributeService from '@/api/basic/orderattribute/OrderAttributeService'
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
            unitOfMeasurement: '',
            inventoryAttribute: ''
          },
          defaultWareHouse: {
            id: '',
            name: ''
          },
          planProduceNum: '',
          orderAttribute: '',
          orderDate: '',
          deliveryDate: '',
          planFinishDate: '',
          trueFinishDate: ''
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
      init(method, obj, orderAttrId) {
        this.method = method
        if (method === 'add') {
          this.title = `新建生产计划单详情`
        } else if (method === 'edit') {
          this.title = '修改生产计划单详情'
        } else if (method === 'view') {
          this.title = '查看生产计划单详情'
        }
        this.visible = true
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.orderAttributes = []
          this.getAttrList(orderAttrId)
          this.inputForm.id = ''
          this.oldInputForm = ''
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.oldInputForm = obj
            this.inputForm = JSON.parse(JSON.stringify(obj))
          }
        })
      },
      // 表单提交
      doSubmit() {
        var _this = this
        for (var i = 0; i < _this.attrList.length; i++) {
          let jsonData = {}
          jsonData.name = _this.attrList[i].englishName
          jsonData.value = ''
          _this.orderAttributes.push(jsonData)
        }
        _this.loading = true
        _this.inventoryService.queryById(_this.inputForm.inventory.id).then(({
          data
        }) => {
          _this.inputForm.inventory = data
          _this.$refs['inputForm'].validate((valid) => {
            if (valid) {
              var json = []
              for (var i = 0; i < _this.orderAttributes.length; i++) {
                var json1 = {}
                json1[_this.orderAttributes[i].name] = _this.inputForm[_this.orderAttributes[i].name]
                json.push(json1)
              }
              _this.inputForm.orderAttribute = JSON.stringify(json)
              _this.$emit('addRow', _this.oldInputForm, JSON.parse(JSON.stringify(_this.inputForm)))
              _this.visible = false
            }
          })
          _this.loading = false
        })
      },
      continueDoSubmit() {
        this.loading = true
        var _this = this
        for (var i = 0; i < _this.attrList.length; i++) {
          let jsonData = {}
          jsonData.name = _this.attrList[i].englishName
          jsonData.value = ''
          _this.orderAttributes.push(jsonData)
        }
        _this.inventoryService.queryById(this.inputForm.inventory.id).then(({
          data
        }) => {
          _this.inputForm.inventory = data
          _this.$refs['inputForm'].validate((valid) => {
            if (valid) {
              var json = []
              for (var i = 0; i < _this.orderAttributes.length; i++) {
                var json1 = {}
                json1[_this.orderAttributes[i].name] = _this.inputForm[_this.orderAttributes[i].name]
                json.push(json1)
              }
              _this.inputForm.orderAttribute = JSON.stringify(json)
              _this.$emit('addRow', _this.oldInputForm, JSON.parse(JSON.stringify(_this.inputForm)))
              this.$refs.inputForm.resetFields()
              this.orderAttributes = []
              this.attrList = []
              this.getAttrList()
              this.inputForm.id = ''
              this.oldInputForm = ''
              this.orderAttributes = []
            }
          })
          _this.loading = false
        })
      },
      inventoryNameChange(data) {
        this.inputForm.inventory = data[0]
        this.inputForm.defaultWareHouse = data[0].inventoryWarehouse.warehouse
      },
      getAttrList(orderAttrId) {
        //根据所选客户的订单属性来自动填充详情
        this.orderAttributeService.queryById1(orderAttrId).then(({
          data
        }) => {
          this.attrList = data
        })
      }
    }
  }
</script>
