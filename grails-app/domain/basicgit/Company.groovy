package basicgit

import grails.converters.JSON

class Company {
    String staffName
    String code
    String gender
    String address
    String salary

    Date lastUpdated
    Date dateCreated
    static belongsTo = [productCategory:ProductCategory]
    static constraints = {
    }
    static {
        JSON.registerObjectMarshaller(Company,{ Company company->
            Map map=new LinkedHashMap<>(company.properties)
            map.id=company.id
            map.remove("productCategory")
            map.remove("productCategoryId")
            return map
        })
    }
}
