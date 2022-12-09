package basicgit

import grails.converters.JSON

class Product {
    String productName
    String code
    String productYear
    String price

    Date lastUpdated
    Date dateCreated
    static belongsTo = [productCategory:ProductCategory]
    static constraints = {
    }
    static {
        JSON.registerObjectMarshaller(Product,{ Product product->
                Map map=new LinkedHashMap<>(product.properties)
                map.id=product.id
                map.remove("productCategory")
                map.remove("productCategoryId")
                return map
        })
    }
}
