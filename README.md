Для дз использовался паттерн Сага (хореография)

`компенсируемая | order-service    | createOrder()    | cancelOrder()`

`компенсируемая | store-service    | createReserve()  | cancelReserve()`

`поворотная     | payment-service  | createPayment()  | cancelPayment() `

`повторяемая    | delivery-service | createDelivery() |`

