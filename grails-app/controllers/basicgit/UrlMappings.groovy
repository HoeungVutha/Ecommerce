package basicgit

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        // Product
        "/api/product"(resources:"Product")

        // ProductCategory
        "/api/productCategory"(resources:"ProductCategory")

        // Company
        "/api/company"(resources:"Company")

        // Booking
        "/api/booking"(resources:"Booking")

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
