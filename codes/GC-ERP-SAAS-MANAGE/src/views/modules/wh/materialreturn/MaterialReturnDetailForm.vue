<template>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible">
    <el-form size="small" :model="inputForm" ref="inputForm" :class="method==='view'?'readonly':''"  :disabled="method==='view'" @keyup.enter.native="doSubmit()"
             label-width="120px">
      <el-row  :gutter="15">
       
        <el-col :span="12">
            <el-form-item label="物料" prop="inventory.id"
                :rules="[
                  {required: true, message:'物料不能为空', trigger:'blur'}
                 ]">
                   <goods-select v-if="materialPlanId"   :limit="1"  :value = "inputForm.materialPlanDetail.id"  
              @getValue='(value) => {inputForm.materialPlanDetail.id=value}' 
               :materialPlanId="materialPlanId" @valueChange="salesorderDetailChange">
               </goods-select>
              
               <inventory-select v-else :limit='1' :value="inputForm.inventory.id"  @getValue='(value) => {inputForm.inventory.id=value}'  @changeValue='changeGoods' ></inventory-select>

          
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
            <el-form-item label="退回数量" prop="num"
                :rules="[
                  {required: true, message:'数量不能为空', trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.num" placeholder="请填写数量"     ></el-input>
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
  import GridSelect from '@/components/gridSelect'
  import InventorySelect from '@/components/inventorySelect'
  import GoodsSelect from './goodsSelect.vue'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        oldInputForm: '',
        materialPlanId: '',
        inputForm: {
          id: '',
          return: {
            id: ''
          },
          materialPlanDetail: {
            id: ''
          },
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
        }
      }
    },
    components: {
      GridSelect,
      InventorySelect,
      GoodsSelect
    },
    methods: {
      init (materialPlanId, method, obj) {
        this.materialPlanId = materialPlanId
        this.method = method
        if (method === 'add') {
          this.title = `新建退料详情`
        } else if (method === 'edit') {
          this.title = '修改退料详情'
        } else if (method === 'view') {
          this.title = '查看退料详情'
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
      salesorderDetailChange(datas) {
        var data = datas[0]
        this.inputForm.inventory = data.inventory
      },
      changeGoods(data) {
        this.inputForm.inventory = data[0]
      }
    }
  }
</script>

  
