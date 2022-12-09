package basicgit

import grails.converters.JSON

class ProductCategory {
     String productName
     String code
     String productCountry
     String price

    Date lastUpdated
    Date dateCreated
    static hasMany = [
                        product:Product ,
                        company:Company
                     ]
    static constraints = {
    }
    static {
        JSON.registerObjectMarshaller(ProductCategory,{ ProductCategory productCategory->
            Map map=new LinkedHashMap<>(productCategory.properties)
            map.id=productCategory.id
            return map
        })
    }
}
