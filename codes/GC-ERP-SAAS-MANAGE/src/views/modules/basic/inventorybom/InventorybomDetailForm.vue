<template>
  <el-dialog :title="title" :close-on-click-modal="false" v-dialogDrag :visible.sync="visible">
    <el-form size="small" :model="inputForm" ref="inputForm" :class="method==='view'?'readonly':''"
      :disabled="method==='view'" @keyup.enter.native="doSubmit()" label-width="120px">
      <el-row :gutter="15">
        <el-col :span="12">
            <el-form-item label="存货" prop="inventory.id" :rules="[
                  {required: true, message:'请选择存货', trigger:'blur'}
                 ]">
                 <inventory-select :limit='1' :value="inputForm.inventory.id"
                 :params="{}"
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
        <el-col :span="12">
          <el-form-item label="在库库存" prop="unitOfMeasurement">
            <el-input v-model="inputForm.inventory.unitOfMeasurement" placeholder="" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="可用库存" prop="inventoryAttribute">
            <el-input v-model="inputForm.inventory.inventoryAttribute" placeholder="" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="所属仓库" prop="name">
            <el-input v-model="inputForm.inventory.preStorageWarehouse.name" placeholder="" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="存货属性" prop="model">
            <el-input v-model="inputForm.inventory.nventoryAttributeValue" placeholder="" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="所需数量" prop="num" :rules="[
                  {required: true, message:'所需数量不能为空', trigger:'blur'}
                 ]">
            <el-input v-model="inputForm.num" placeholder="请填写所需数量" type="number"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="备注" prop="remark" :rules="[
                 ]">
            <el-input v-model="inputForm.remark" placeholder="请填写备注"></el-input>
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
            inventoryAttribute: '',
            preStorageWarehouse: {
              id: '',
              name: ''
            }
          },
          num: '',
          remark: ''
        }
      }
    },
    components: {
      GridSelect,InventorySelect
    },
    inventoryService: null,
    created() {
      this.inventoryService = new InventoryService()
    },
    methods: {
      init(method, obj) {
        this.method = method
        if (method === 'add') {
          this.title = `新建物料清单详情`
        } else if (method === 'edit') {
          this.title = '修改物料清单详情'
        } else if (method === 'view') {
          this.title = '查看物料清单详情'
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
      doSubmit() {
        this.loading = true
        this.inventoryService.queryById(this.inputForm.inventory.id).then(({
          data
        }) => {
          this.inputForm.inventory = data
          this.$refs['inputForm'].validate((valid) => {
            if (valid) {
              this.$emit('addRow', this.oldInputForm, JSON.parse(JSON.stringify(this.inputForm)))
              this.visible = false
            }
          })
          this.loading = false
        })
      },
      continueDoSubmit() {
        this.loading = true
        this.inventoryService.queryById(this.inputForm.inventory.id).then(({
          data
        }) => {
          this.inputForm.inventory = data
          this.$refs['inputForm'].validate((valid) => {
            if (valid) {
              this.$emit('addRow', this.oldInputForm, JSON.parse(JSON.stringify(this.inputForm)))
              this.$refs['inputForm'].resetFields()
            }
          })
          this.loading = false
        })
      },
      inventoryNameChange(data) {
        this.inputForm.inventory = data[0]
      }
    }
  }
</script>
