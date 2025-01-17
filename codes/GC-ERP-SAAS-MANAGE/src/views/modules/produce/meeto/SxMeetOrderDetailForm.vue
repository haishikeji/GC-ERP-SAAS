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
            <el-form-item label="存货" prop="inventory.id"
                :rules="[
                 ]">
          <GridSelect
            title="选择存货"
            labelName = 'inventoryName'
            labelValue = 'id'
            :disabled="true"
            :value = "inputForm.inventory.id"
            :limit="1"
            @getValue='(value) => {inputForm.inventory.id=value}'
            :columns="[
            {
              prop: 'id',
              label: 'id'
            },
            {
              prop: 'inventoryName',
              label: '存货名称'
            },
            {
              prop: 'inventoryCode',
              label: '存货编码'
            },
            {
              prop: 'model',
              label: '规格型号'
            },
            {
              prop: 'nventoryAttributeValue',
              label: '存货属性值'
            },
            {
              prop: 'singalUnit',
              label: '计量单位'
            },
            {
              prop: 'notaxPrice',
              label: '未税单价'
            },
            {
              prop: 'taxincludedPrice',
              label: '含税单价'
            },
            {
              prop: 'unitOfMeasurement',
              label: '在库库存'
            },
            {
              prop: 'inventoryAttribute',
              label: '可用库存'
            },
            {
              prop: 'lockStock',
              label: '锁定库存'
            }
            ]"
            :searchs="[
            {
              prop: 'id',
              label: 'id'
            },
            {
              prop: 'inventoryName',
              label: '存货名称'
            },
            {
              prop: 'inventoryCode',
              label: '存货编码'
            },
            {
              prop: 'model',
              label: '规格型号'
            },
            {
              prop: 'nventoryAttributeValue',
              label: '存货属性值'
            },
            {
              prop: 'singalUnit',
              label: '计量单位'
            },
            {
              prop: 'notaxPrice',
              label: '未税单价'
            },
            {
              prop: 'taxincludedPrice',
              label: '含税单价'
            },
            {
              prop: 'unitOfMeasurement',
              label: '在库库存'
            },
            {
              prop: 'inventoryAttribute',
              label: '可用库存'
            },
            {
              prop: 'lockStock',
              label: '锁定库存'
            }
            ]"
            dataListUrl="/basic/inventorytype/inventory/list"
            queryEntityUrl="/basic/inventorytype/inventory/queryById">
          </GridSelect>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="采购数量" prop="purOrderNum"
                :rules="[
                 ]">
              <el-input v-model="inputForm.purOrderNum" placeholder="请填写采购数量"     :disabled="true"></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="已入库数量" prop="purUsedNum"
                :rules="[
                 ]">
              <el-input v-model="inputForm.purUsedNum" placeholder="请填写已入库数量"    :disabled="true" ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="未入库数量" prop="purUnusedNum"
                :rules="[
                 ]">
              <el-input v-model="inputForm.purUnusedNum" placeholder="请填写未入库数量"    :disabled="true" ></el-input>
           </el-form-item>
        </el-col>
     <!--   <el-col :span="12">
            <el-form-item label="供货时间" prop="supplyDate"
                :rules="[
                 ]">
              <el-input v-model="inputForm.supplyDate" placeholder="请填写供货时间"    :disabled="true" ></el-input>
           </el-form-item> -->
        </el-col>
        <el-col :span="12">
            <el-form-item label="单价" prop="unitPrice"
                :rules="[
                 ]">
              <el-input v-model="inputForm.unitPrice" placeholder="请填写单价"     :disabled="true"></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="总价" prop="totalPrice"
                :rules="[
                 ]">
              <el-input v-model="inputForm.totalPrice" placeholder="请填写总价"    :disabled="true" ></el-input>
           </el-form-item>
        </el-col>
        <!-- <el-col :span="12">
            <el-form-item label="订单属性" prop="orderAttribute"
                :rules="[
                 ]">
              <el-input v-model="inputForm.orderAttribute" placeholder="请填写订单属性"     ></el-input>
           </el-form-item>
        </el-col> -->
        <el-col :span="12">
            <el-form-item label="已入庫" prop="received"
                :rules="[
                 ]">
              <el-input v-model="inputForm.received" placeholder="请填写已入庫"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="应付款金额" prop="payableAmount"
                :rules="[
                 ]">
              <el-input v-model="inputForm.payableAmount" placeholder="请填写应付款金额"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="剩余付款金额" prop="remainingAmount"
                :rules="[
                 ]">
              <el-input v-model="inputForm.remainingAmount" placeholder="请填写剩余付款金额"     ></el-input>
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
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        oldInputForm: '',
        inputForm: {
          id: '',
          purOrder: {
            id: ''
          },
          purSupplier: '',
          inventory: {
            id: ''
          },
          purOrderNum: '',
          purUsedNum: '',
          purUnusedNum: '',
          supplyDate: '',
          unitPrice: '',
          totalPrice: '',
          orderAttribute: '',
          received: '',
          payableAmount: '',
          remainingAmount: ''
        }
      }
    },
    components: {
      GridSelect
    },
    methods: {
      init (method, obj) {
        this.method = method
        if (method === 'add') {
          this.title = `新建应付订单详情`
        } else if (method === 'edit') {
          this.title = '修改应付订单详情'
        } else if (method === 'view') {
          this.title = '查看应付订单详情'
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
      }
    }
  }
</script>
