package com.mini.asaas.payment

import com.mini.asaas.base.BasePaymentAdapter
import com.mini.asaas.utils.BigDecimalUtils
import com.mini.asaas.utils.DateFormatUtils
import com.mini.asaas.utils.Utils

class PaymentSaveAdapter extends BasePaymentAdapter {

    public PaymentSaveAdapter(Map originalParams) {
        Map<String, String> params = Utils.normalizeParams(originalParams)
        if (!params) return
        this.payerId = params.payerId as Long
        this.value = BigDecimalUtils.fromFormattedString(params.value)
        this.description = params.description
        this.billingType = BillingType.convert(params.billingType)
        this.status = PaymentStatus.PENDING
        this.dueDate = DateFormatUtils.parseDateFromString(params.dueDate)
    }
}
