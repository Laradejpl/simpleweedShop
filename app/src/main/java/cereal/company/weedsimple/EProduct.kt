package cereal.company.weedsimple

class EProduct {

    var id: Int
    var name: String
    var price: Int
    var pictureName: String
    var stock: Int


    constructor(id: Int, name: String, price:Int, picture:String,stock: Int){


        this.id = id
        this.name = name
        this.price = price
        this.pictureName = picture
        this.stock = stock

    }



}