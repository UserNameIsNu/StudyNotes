-- KEYS[1] = 商品库存ZSet key ("goodsZSet")
-- KEYS[2] = 订单Hash key ("ordersHash")
-- ARGV[1] = 商品ID
-- ARGV[2] = 订单ID（UUID）
-- ARGV[3] = 用户ID
-- ARGV[4] = 数量

local stock = redis.call('ZSCORE', KEYS[1], ARGV[1])
if (not stock) or (tonumber(stock) < tonumber(ARGV[4])) then
    return 0
end

redis.call('ZINCRBY', KEYS[1], -tonumber(ARGV[4]), ARGV[1])

local order = cjson.encode({orderId=ARGV[2], goodsId=ARGV[1], userId=ARGV[3], num=ARGV[4]})
redis.call('HSET', KEYS[2], ARGV[2], order)

return 1
