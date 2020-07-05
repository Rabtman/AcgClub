package com.rabtman.common.base.mvp

import com.rabtman.common.integration.IRepositoryManager

/**
 * Created by jess on 8/5/16 12:55
 * contact with jess.yan.effort@gmail.com
 */
open class BaseModel(//用于管理网络请求层,以及数据缓存层
        protected var mRepositoryManager: IRepositoryManager) : IModel {

    override fun onDestroy() {
    }

}