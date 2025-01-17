<template>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible" width="70%">
    <el-form size="small" :model="inputForm" ref="inputForm" :class="method==='view'?'readonly':''"  :disabled="method==='view'" @keyup.enter.native="doSubmit()"
             label-width="120px">
      <el-row  :gutter="15">
        <el-col :span="12">
            <el-form-item label="存货" prop="inventory.inventoryWarehouse.id"
                :rules="[
                  {required: true, message:'请选择调出存货', trigger:'blur'}
                 ]">

               <inventory-warehouse-select   :limit='1' :value="inputForm.inventory.inventoryWarehouse.id" :params="{'preStorageWarehouseId':warehouseId}"  @changeValue="setValue"></inventory-warehouse-select>

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
          <el-form-item label="所在仓库" prop="inventory.inventoryWarehouse.warehouse.name">
            <el-input v-model="inputForm.inventory.inventoryWarehouse.warehouse.name" placeholder="" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
         <el-col :span="12">
            <el-form-item label="在库库存" prop="inventory.inventoryWarehouse.inventoryInStock" >
              <el-input v-model="inputForm.inventory.inventoryWarehouse.inventoryInStock" placeholder="在库库存" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="可用库存" prop="inventory.inventoryWarehouse.availableStock" >
              <el-input v-model="inputForm.inventory.inventoryWarehouse.availableStock" placeholder="可用库存" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="锁定库存" prop="inventory.inventoryWarehouse.lockStock" >
              <el-input v-model="inputForm.inventory.inventoryWarehouse.lockStock" placeholder="锁定库存" :disabled="true"></el-input>
            </el-form-item>
          </el-col>

        <el-col :span="12">
            <el-form-item label="调拨数量" prop="num"
                :rules="numRules">
              <el-input v-model="inputForm.num" placeholder="请填写调拨数量"     ></el-input>
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
  import InventorySelect from '@/components/inventorySelect'
  import InventoryWarehouseSelect from '@/components/inventoryWarehouseSelect'
  export default {
    data () {
      var validateNum = (rule, value, callback) => {
        var _this = this
        if (value === '') {
          callback(new Error('请输入调拨数量'))
        } else if (Number(value) > Number(_this.inputForm.inventory.inventoryWarehouse.availableStock)) {
          callback(new Error('调拨数量大于可用库存!'))
        } else {
          callback()
        }
      }
      return {
        warehouseId: '',
        title: '',
        method: '',
        visible: false,
        oldInputForm: '',
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
          num: ''

        },
        numRules: [
                  {validator: this.validator.isIntGtZero, trigger: 'blur'},
                  {required: true, message: '请输入调拨数量', trigger: 'blur'},
                  {validator: validateNum, trigger: 'blur'}
        ]
      }
    },
    components: {
      InventorySelect,
      InventoryWarehouseSelect
    },
    methods: {
      init (warehouseId, method, obj) {
        this.warehouseId = warehouseId
        this.method = method
        if (method === 'add') {
          this.title = `新建仓库调拨详情`
        } else if (method === 'edit') {
          this.title = '修改仓库调拨详情'
        } else if (method === 'view') {
          this.title = '查看仓库调拨详情'
        }
        this.visible = true
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.inputForm.id = ''
          this.oldInputForm = ''
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.oldInputForm = obj
            this.inputForm = JSON.parse(JSON.stringify(obj))
          }
        })
      },
   // 表单提交
      doSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.$emit('addRow', this.oldInputForm, JSON.parse(JSON.stringify(this.inputForm)))
            this.visible = false
          }
        })
      },
      continueDoSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.$emit('addRow', this.oldInputForm, JSON.parse(JSON.stringify(this.inputForm)))
            this.$refs['inputForm'].resetFields()
          }
        })
      },
      setValue(datas) {
        var data = datas[0]
        this.inputForm.inventory = data
      }
    }
  }
</script>


