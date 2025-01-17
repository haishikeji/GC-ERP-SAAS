<template>
  <div>
    <el-dialog :title="title" :close-on-click-modal="false" v-dialogDrag :visible.sync="visible" width="70%">
      <el-form :model="inputForm" size="small" ref="inputForm" v-loading="loading"
        :class="method==='view'?'readonly':''" :disabled="method==='view'" label-width="120px">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="存货类别" prop="inventoryType.id" :rules="[
                  {required: true, message:'存货类别不能为空', trigger:'blur'}
                 ]">
              <SelectTree ref="inventoryType" :props="{
                          value: 'id',             // ID字段名
                          label: 'name',         // 显示名称
                          children: 'children'    // 子级字段名
                        }" url="/basic/inventorytype/inventoryType/treeData" :value="inputForm.inventoryType.id"
                :clearable="true" :accordion="true" @getValue="(value) => {inputForm.inventoryType.id=value}" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="存货编码" prop="inventoryCode" :rules="[
               {required: true, message:'存货编码不能为空', trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.inventoryCode" placeholder="请填写存货编码"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="存货名称" prop="inventoryName" :rules="[
                  {required: true, message:'存货名称不能为空', trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.inventoryName" placeholder="请填写存货名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="计量方式" prop="measurementMethod" :rules="[
                  {required: true, message:'计量方式不能为空', trigger:'blur'}
                 ]">
              <el-select id="measurementMethod" v-model="inputForm.measurementMethod" placeholder="请选择"
                style="width: 100%;">
                <el-option v-for="item in $dictUtils.getDictList('measurement_method')" :key="item.value"
                  :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="主计量单位" prop="singalUnit" >
              <el-select id="singalUnit" v-model="inputForm.singalUnit" placeholder="请选择" style="width: 100%;"
                ref="singalUnit" @change="getUnitData">
                <el-option v-for="item in $dictUtils.getDictList('sx_metering_unit')" :key="item.value"
                  :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="采购常用单位" prop="purchaseCommonUnit" :rules="[
                 ]">
              <el-input :disabled="true" v-model="purchaseCommonUnitName" placeholder="请填写采购常用单位"
                v-if="inputForm.measurementMethod != 2"></el-input>
              <el-select v-model="inputForm.purchaseCommonUnit" placeholder="请填写采购常用单位" style="width: 100%;"
                v-if="inputForm.measurementMethod == 2">
                <el-option v-for="item in purchaseCommonUnitList" :key="item.id" :label="item.label"
                  :value="item.unitName">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="销售常用单位" prop="saleCommonUnit" :rules="[
                 ]">
              <el-input :disabled="true" v-model="saleCommonUnitName" placeholder="请填写销售常用单位"
                v-if="inputForm.measurementMethod != 2"></el-input>
              <el-select v-model="inputForm.saleCommonUnit" placeholder="请填写销售常用单位" style="width: 100%;"
                v-if="inputForm.measurementMethod == 2">
                <el-option v-for="item in saleCommonUnitList" :key="item.id" :label="item.label" :value="item.unitName">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="规格型号" prop="model" :rules="[
                 ]">
              <el-input v-model="inputForm.model" placeholder="请填写规格型号"></el-input>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="12">
            <el-form-item label="品牌" prop="brand" :rules="[
                 ]">
              <el-input v-model="inputForm.brand" placeholder="请填写存货品牌" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="厂家" prop="manufactor" :rules="[
                 ]">
              <el-input v-model="inputForm.manufactor" placeholder="请填写存货生产厂家" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生产制造地" prop="placeoforigin" :rules="[
                 ]">
              <el-input v-model="inputForm.placeoforigin" placeholder="请填写存货生产厂家" :disabled="true"></el-input>
            </el-form-item>
          </el-col> -->
          <el-col :span="12">
            <el-form-item label="预存仓库" prop="preStorageWarehouse.id" :rules="[
                  {required: true, message:'预存仓库不能为空', trigger:'blur'}
                 ]">
              <GridSelect title="选择预存仓库" labelName='name' labelValue='id' :value="inputForm.preStorageWarehouse.id"
                :limit="1" @getValue='(value) => {inputForm.preStorageWarehouse.id=value}' :columns="[
            {
              prop: 'name',
              label: '仓库名称'
            }
            ]" :searchs="[
            {
              prop: 'name',
              label: '仓库名称'
            }
            ]" dataListUrl="/basic/warehouse/wareHouse/list" queryEntityUrl="/basic/warehouse/wareHouse/queryById">
              </GridSelect>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="税率点" prop="taxRatePoint" :rules="[
                  {required: true, message:'税率点不能为空', trigger:'blur'},
                  {pattern: /^[1-9]\d*$/, message: '请输入正整数',trigger: 'blur'}
                 ]">
              <el-input v-model="inputForm.taxRatePoint" placeholder="请填写税率点"  max="100"  min="0" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="未税单价(元)" prop="notaxPrice" :rules="[
                  {required: true, message:'未税单价不能为空', trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.notaxPrice" placeholder="请填写未税单价-填写后请在输入框外空白处点击一下" @blur="notaxPriceChange()" type="number"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="含税单价(元)" prop="taxincludedPrice" :rules="[
                  {required: true, message:'含税单价不能为空', trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.taxincludedPrice" placeholder="请填写含税单价-填写后请在输入框外空白处点击一下" @blur="taxincludedPriceChange()"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24" v-if="inputForm.nventoryAttributeValue">
            <el-form-item label="存货属性值" prop="nventoryAttributeValue" v-if="inputForm.id != ''">
                  {{inputForm.nventoryAttributeValue}}
            </el-form-item>
          </el-col>

          <el-col :span="24" v-if="inputForm.inventoryWarehouseDTOList.length>0 " >
            <el-form-item label-width="0">
              <el-tabs >
                <el-tab-pane label="仓库库存">

                  <el-table
                    class="table"
                    size="small"
                    :data=" inputForm.inventoryWarehouseDTOList "
                    style="width: 100%"
                  >
                    <el-table-column prop="warehouse.name" header-align="center" align="center" show-overflow-tooltip label="仓库" >
                    </el-table-column>
                     <el-table-column prop="minimumInventory" header-align="center" align="center"  show-overflow-tooltip label="最小库存" >
                       <template slot-scope="scope">
                        <el-input v-model="scope.row.minimumInventory" type="number" ></el-input>
                      </template>
                    </el-table-column>
                     <el-table-column prop="maximumInventory" header-align="center" align="center"  show-overflow-tooltip label="最大库存" >
                       <template slot-scope="scope">
                        <el-input v-model="scope.row.maximumInventory"  type="number"></el-input>
                      </template>
                    </el-table-column>
                     <el-table-column prop="inventoryInStock" header-align="center" align="center" show-overflow-tooltip label="在库库存" >
                       <template slot-scope="scope">
                        <el-input v-model="scope.row.inventoryInStock" type="number" @change="scope.row.availableStock=scope.row.inventoryInStock" ></el-input>
                      </template>
                    </el-table-column>
                    <el-table-column prop="availableStock" header-align="center" align="center" show-overflow-tooltip label="可用库存" >
                    </el-table-column>
                    <el-table-column prop="lockStock" header-align="center" align="center" show-overflow-tooltip label="锁定库存" >
                    </el-table-column>
                  </el-table>
                </el-tab-pane>
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
  import SelectTree from '@/components/treeSelect/treeSelect.vue'
  import GridSelect from '@/components/gridSelect'
  import InventoryService from '@/api/basic/inventorytype/InventoryService'
  import MeteringUnitService from '@/api/basic/unit/MeteringUnitService'
  export default {
    data() {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        purchaseCommonUnitList: [],
        saleCommonUnitList: [],
        purchaseCommonUnitName: '',
        saleCommonUnitName: '',
        inputForm: {
          id: '',
          inventoryType: {
            id: ''
          },
          inventoryCode: '',
          inventoryName: '',
          measurementMethod: '',
          singalUnit: '',
          purchaseCommonUnit: '',
          saleCommonUnit: '',
          model: '',
          preStorageWarehouse: {
            id: ''
          },
          minimumInventory: '',
          maximumInventory: '',
          unitOfMeasurement: '',
          inventoryAttribute: '',
          lockStock: '',
          taxRatePoint: '13',
          notaxPrice: '',
          taxincludedPrice: '',
          nventoryAttribute: {
            id: ''
          },
          nventoryAttributeValue: '',
          brand: '',
          manufactor: '',
          placeoforigin: '',
          inventoryWarehouseDTOList: []
        },
        attributeList: []
      }
    },
    components: {
      SelectTree,
      GridSelect
    },
    inventoryService: null,
    created() {
      this.inventoryService = new InventoryService()
      this.meteringUnitService = new MeteringUnitService()
    },
    methods: {
      init(method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建存货`
        } else if (method === 'edit') {
          this.title = '修改存货'
        } else if (method === 'view') {
          this.title = '查看存货'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          if (method === 'edit' || method === 'view') {
            this.loading = true
            this.inventoryService.queryById(this.inputForm.id).then(({
              data
            }) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.loading = false
            })
          }
        })
      },
      doSubmit() {
        this.taxincludedPriceChange()
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            if (!this.inputForm.nventoryAttribute.id) {
              this.inputForm.nventoryAttributeValue = ''
            }
            this.inventoryService.update(this.inputForm).then(({
              data
            }) => {
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
      getUnitData() {
        if (Number(this.inputForm.measurementMethod) === 2) {
          this.loading = true
          this.meteringUnitService.list({
            'current': 1,
            'size': 999,
            'unitId': this.inputForm.singalUnit
          }).then(({
            data
          }) => {
            this.inputForm.purchaseCommonUnit = ''
            this.inputForm.saleCommonUnit = ''
            this.purchaseCommonUnitList = data.records
            this.saleCommonUnitList = data.records
            this.loading = false
          })
        } else if (Number(this.inputForm.measurementMethod) === 1) {
          this.inputForm.purchaseCommonUnit = this.inputForm.singalUnit
          this.inputForm.saleCommonUnit = this.inputForm.singalUnit
          this.$nextTick(() => {
            this.purchaseCommonUnitName = this.$refs.singalUnit.selectedLabel
            this.saleCommonUnitName = this.$refs.singalUnit.selectedLabel
          })
        }
      },
      attributeChange(datas) {
        var _this = this
        _this.attributeList = []
        _this.inputForm.inventoryWarehouseDTOList = []
        datas.map((item) => {
          var children = []
          item.children.map((child) => {
            children.push({'id': child.id, 'name': child.name, 'checked': false})
          })
          _this.attributeList.push({'name': item.name, 'children': children})
        })
      },
      createTable() {
        var _this = this
        _this.inputForm.inventoryWarehouseDTOList = []
        var item = _this.attributeList[0]
        var td = {
          inventory: {id: '', nventoryAttributeValue: ''},
          warehouse: {id: '', name: ''},
          minimumInventory: 0,
          maximumInventory: 0,
          inventoryInStock: 0,
          availableStock: 0,
          lockStock: 0
        }
        var yb = 1
        for (const index in item.children) {
          var child = item.children[index]
          if (child.checked) {
            td.inventory.nventoryAttributeValue = item.name + ':' + child.name
            if (_this.attributeList.length === yb) {
              var temp = JSON.parse(JSON.stringify(td))
              _this.inputForm.inventoryWarehouseDTOList.push(temp)
            } else {
              _this.toSetValue(td, yb)
            }
          }
        }
      },
      toSetValue(td, yb) {
        var _this = this
        yb++
        var item = _this.attributeList[yb - 1]
        var unCheckedNum = 0
        var nventoryAttributeValue = td.inventory.nventoryAttributeValue
        for (const index in item.children) {
          var child = item.children[index]
          if (child.checked) {
            td.inventory.nventoryAttributeValue = nventoryAttributeValue + ',' + item.name + ':' + child.name
            if (_this.attributeList.length === yb) {
              let temp = JSON.parse(JSON.stringify(td))
              _this.inputForm.inventoryWarehouseDTOList.push(temp)
            } else {
              _this.toSetValue(td, yb)
            }
          } else {
            unCheckedNum++
          }
          if (unCheckedNum === item.children.length) {
            if (_this.attributeList.length === yb) {
              let temp = JSON.parse(JSON.stringify(td))
              _this.inputForm.inventoryWarehouseDTOList.push(temp)
            } else {
              _this.toSetValue(td, yb)
            }
          }
        }
      },
     //计算含税价格
     notaxPriceChange(){
       if(!this.inputForm.taxRatePoint){
         this.$message.error("请填写税率点，0-100的整数")
         return;
       }
       if(Number(this.inputForm.taxRatePoint)>=0 && Number(this.inputForm.taxRatePoint)<=100){
         this.inputForm.taxincludedPrice = this.$dateUtils.NumberMul(this.inputForm.notaxPrice, this.$dateUtils
           .NumberDiv((Number(this.inputForm.taxRatePoint)+100), 100.0,2))


       }else{
         this.$message.error("税率点请输入0-100的整数")
         return;
       }

     },
     //计算未税价格
     taxincludedPriceChange(){
       if(!this.inputForm.taxRatePoint){
         this.$message.error("请填写税率点，0-100的整数")
         return;
       }
       if(Number(this.inputForm.taxRatePoint)>=0 && Number(this.inputForm.taxRatePoint)<=100){
         this.inputForm.notaxPrice = this.$dateUtils.NumberMul(this.inputForm.taxincludedPrice, this.$dateUtils
           .NumberDiv(this.$dateUtils.NumberSub(100,Number(this.inputForm.taxRatePoint)), 100.0,2))
       }else{
         this.$message.error("税率点请输入0-100的整数")
         return;
       }
     }

    }
  }
</script>
