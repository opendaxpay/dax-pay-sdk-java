package cn.daxpay.open.sdk.param;

/// # 订单商品明细（单个）
///
/// 对照契约 6.6 节。用于单品营销 / 电子发票场景，挂在 PayParam 与 GatewayPrePayParam 上。
public class GoodsDetail {

    /// 商户侧商品编码（必填）
    private String goodsId;
    /// 商品名称（必填）
    private String goodsName;
    /// 商品数量（必填，≥1）
    private Integer quantity;
    /// 商品单价（分，必填）
    private Long unitPrice;
    /// 商品分类（支付宝独有）
    private String category;
    /// 商品描述
    private String description;
    /// 商品展示链接
    private String showUrl;

    public String getGoodsId() {
        return goodsId;
    }

    public GoodsDetail setGoodsId(String goodsId) {
        this.goodsId = goodsId;
        return this;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public GoodsDetail setGoodsName(String goodsName) {
        this.goodsName = goodsName;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public GoodsDetail setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public GoodsDetail setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public GoodsDetail setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public GoodsDetail setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getShowUrl() {
        return showUrl;
    }

    public GoodsDetail setShowUrl(String showUrl) {
        this.showUrl = showUrl;
        return this;
    }
}
