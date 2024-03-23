class Manager {
    //Функция припарковать машину: пробегает циклом while массив parcking
    fun parkTheCar(car: Car, parking: MutableMap<String, String?>) {
        var i = 1
        while (i <= parking.size) {
            if (parking["P$i"] == null) {
                parking["P$i"] = "${car.color} ${car.brand} ${car.number} ${car.owner}"
                println("Машина поставлена на парковку")
                break
            } else if (i == parking.size) {
                println("Мест нет!")
                break
            }
            i += 1

        }

    }

    // Функция забрать машину с парковки
    fun pickUp(number: String, parking: MutableMap<String, String?>, ownerName: String) {
        var i = 1
        while (i <= parking.size) {
            if (parking["P$i"] != null) {
                if (parking["P$i"]!!.contains(number)) {
                    if (parking["P$i"]!!.contains(ownerName)) {
                        parking["P$i"] = null
                        println("Машина уехала с парковки")
                        break
                    } else {
                        println("Эта машины не может быть возвращена вам! Вы не ее владелец")
                        break
                    }
                }
            } else if (i == parking.size) {
                println("Такой машины нет на парковке!")
                break
            }
            i += 1
        }
    }

    //Функция получить информацию о машине по номеру
    fun parkInfoByCar(carNumber: String, parking: MutableMap<String, String?>) {
        var i = 1
        while (i <= parking.size) {
            if (parking["P$i"] != null) {
                if (parking["P$i"]!!.contains(carNumber)) {
                    println("Машина стоит на месте № Р$i")
                    break
                }
            }
            i++

        }
        if (i > parking.size) {
            println("Такой машины нет на парковке")
        }


    }

    fun parkInfoByPlace(parking: MutableMap<String, String?>, parkingPlace: String) {
        if (parking[parkingPlace] == null) {
            println("Место № $parkingPlace свободно")
        } else {
            println("На месте $parkingPlace припаркована машина: ${parking[parkingPlace]}")
        }
    }
}