<template>
<div>
    <el-input placeholder="请选择" :size="size" :disabled="disabled"  :readonly="readonly" style="line-hight:40px" v-model="name" class="input-with-select">
      <el-button slot="append" :disabled="disabled"  :readonly="readonly" @click="showSelect" icon="el-icon-search"></el-button>
    </el-input>
    <materil-select-dialog ref="selectDialog" @doSubmit="selectToInput" :limit="limit" :selectData="selectData"  :params="params"></materil-select-dialog>
</div>
</template>
<script>
import MaterilSelectDialog from './materilSelectDialog.vue'
import MaterialPlanService from '@/api/produce/materialplan/MaterialPlanService'
export default {
  data () {
    return {
      name: '',
      labelValue: this.value,
      selectData: []
    }
  },
  props: {
    params: {},
    limit: Number,
    value: String,
    size: {
      type: String,
      default: () => { return 'small' }
    },
    readonly: {
      type: Boolean,
      default: () => { return false }
    },
    disabled: {
      type: Boolean,
      default: () => { return false }
    }
  },
  components: {
    MaterilSelectDialog
  },
  materialPlanService: null,
  beforeCreate () {
    this.materialPlanService = new MaterialPlanService()
  },
  watch: {
    value: {
      handler (newVal) {
        this.selectData = []
        if (newVal) {
          newVal.split(',').forEach((id) => {
            this.materialPlanService.queryById(id).then(({data}) => {
              if (data && data.id !== '') {
                this.selectData.push(data)
              }
            })
          })
        }
      },
      immediate: true,
      deep: false
    },
    selectData: {
      handler (newVal) {
        this.name = newVal.map(data => { return data.materialplanCode }).join(',')
      },
      immediate: false,
      deep: false
    }
  },
  methods: {
    selectToInput (datas) {
      this.selectData = datas
      this.labelValue = datas.map(data => { return data.id }).join(',')
      this.name = datas.map(data => { return data.materialplanCode }).join(',')
      this.$emit('getValue', this.labelValue, this.name)
      this.$emit('changeValue', datas)
      // 多选数据-产品出库调用
      this.$emit('valueChanges', datas, this)
    },
    showSelect () {
      this.$refs.selectDialog.init()
    }
  }
}
</script>
<style>
  .el-form-item__content .el-input-group {
      vertical-align: middle;
  }
 .el-tag + .el-tag {
    margin-left: 5px;
    margin-bottom: 5px;
  }
</style>
