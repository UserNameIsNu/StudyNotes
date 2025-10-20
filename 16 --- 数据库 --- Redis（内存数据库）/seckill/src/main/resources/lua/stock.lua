-- 获取当前库存
local stock = redis.call("GET", KEYS[1])
-- 判断库存是否大于0
if tonumber(stock) > 0 then
    -- 扣减库存，并获得最新的库存量
    stock = redis.call("INCRBY", KEYS[1], -1)
    return stock
else
    -- 当库存为0时返回-1
    return -1
end