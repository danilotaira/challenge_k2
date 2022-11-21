# QikServe Engineer Technical Test

## Coding Challenge - Part one

In this test we used spring boot. All operations are through api.

Java 11 required.

In order to facilitate the presentation, a seed class was created in which to populate some baskets with their items, following the examples presented in the challenge.

To perform the rest calls, please follow the examples below.

### Build and run application:
```bash
./mvnw clean install
java -jar ./target/basket-0.0.1-SNAPSHOT.jar
```

NOTE: Running mockapi required

### Find all baskets registered in the system
GET http://localhost:8080/api/basket
> curl --location --request GET 'http://localhost:8080/api/basket'

Response:
``` json
[
    {
        "id": 3,
        "total": 2397,
        "totalDiscount": 399,
        "totalPayable": 1998,
        "Items": {
            "Boring Fries!": {
                "total": 199,
                "totalPayable": 199,
                "qty": 1,
                "totalDiscount": 0
            },
            "Amazing Pizza!": {
                "total": 2198,
                "totalPayable": 1799,
                "qty": 2,
                "totalDiscount": 399
            }
        }
    },
    {
        "id": 4,
        "total": 3496,
        "totalDiscount": 1048.9,
        "totalPayable": 2447.1,
        "Items": {
            "Amazing Salad!": {
                "total": 499,
                "totalPayable": 449.1,
                "qty": 1,
                "totalDiscount": 49.9
            },
            "Amazing Burger!": {
                "total": 2997,
                "totalPayable": 1998,
                "qty": 3,
                "totalDiscount": 999
            }
        }
    }
]
```
### Find basket by Id registered in the system
GET http://localhost:8080/api/basket/{id}
> curl --location --request GET 'http://localhost:8080/api/basket/4'

Response:
``` json
{
    "id": 4,
    "total": 3496,
    "totalDiscount": 1048.9,
    "totalPayable": 2447.1,
    "items": {
        "Amazing Salad!": {
            "total": 499,
            "totalPayable": 449.1,
            "qty": 1,
            "totalDiscount": 49.9
        },
        "Amazing Burger!": {
            "total": 2997,
            "totalPayable": 1998,
            "qty": 3,
            "totalDiscount": 999
        }
    }
}
```
### Register new basket with some items
POST http://localhost:8080/api/basket
> curl --location --request POST 'http://localhost:8080/api/basket' \
--header 'Content-Type: application/json' \
--data-raw '[
{
"idProduct":"PWWe3w1SDU",
"qty":10
},
{
"idProduct":"C8GDyLrHJb",
"qty":12
}    
]'

Body:
``` json
[
    {
        "idProduct":"PWWe3w1SDU",
        "qty":10
    },
    {
        "idProduct":"C8GDyLrHJb",
        "qty":12
    }    
]
```
Response:
``` json
{
    "id": 6,
    "total": 15978,
    "totalDiscount": 5593.8,
    "totalPayable": 10384.2,
    "items": {
        "Amazing Salad!": {
            "total": 5988,
            "totalPayable": 5389.2,
            "qty": 12,
            "totalDiscount": 598.8
        },
        "Amazing Burger!": {
            "total": 9990,
            "totalPayable": 4995,
            "qty": 10,
            "totalDiscount": 4995
        }
    }
}
```

### Changing items in a basket (adding, removing or changing quantity)
PUT http://localhost:8080/api/basket/{id}

Note: To remove an item you must set the quantity equal to zero
> curl --location --request PUT 'http://localhost:8080/api/basket/6' \
--header 'Content-Type: application/json' \
--data-raw '[
{
"idProduct":"PWWe3w1SDU",
"qty":0
},
{
"idProduct":"C8GDyLrHJb",
"qty":1
}    
]'

Body:
``` json
[
    {
        "idProduct":"PWWe3w1SDU",
        "qty":0
    },
    {
        "idProduct":"C8GDyLrHJb",
        "qty":1
    }    
]
```
Response:
``` json
{
    "id": 6,
    "total": 499,
    "totalDiscount": 49.9,
    "totalPayable": 449.1,
    "items": {
        "Amazing Salad!": {
            "total": 499,
            "totalPayable": 449.1,
            "qty": 1,
            "totalDiscount": 49.9
        }
    }
}
```