package day04.work

/**
  *
  * @ClassName: ListWork02
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/20 19:32
  *
  */
object ListWork02 {
  def main(args: Array[String]): Unit = {

    //  集合元素操作

    //  col :+ ele			将元素添加到集合尾部		Seq
    val seq1 = scala.collection.mutable.Seq(1, 2, 3, 4)
    val seq2 = scala.collection.immutable.Seq(1, 2, 3, 4)
    println(seq1 :+ 5)//ArrayBuffer(1, 2, 3, 4, 5)
    println(seq2 :+ 5)//List(1, 2, 3, 4, 5)

    //  ele +: col			将元素添加到集合头部		Seq
    println(5 +: seq1)// ArrayBuffer(5, 1, 2, 3, 4)
    println(5 +: seq2)   //List(5, 1, 2, 3, 4)

    //  col + ele			在集合尾部添加元素			Set、Map
    val set = scala.collection.mutable.Set(1, 2, 3, 4, 5)
    //    val set = Set(1,2,3,4,5)
    val map = Map(("java"-> 1), ("scala"->2), ("hadoop"-> 3), ("hive"-> 4))

    println(set + 6)  //Set(1, 5, 2, 6, 3, 4)
    println(map + ("hbase" -> 6))  //Map(java -> 1, hadoop -> 3, hive -> 4, scala -> 2, hbase -> 6)

    //  col + (ele1, ele2)	将其他集合添加到集合的尾部	Set、Map
    val set2 = Set(7, 8)
    val map2 = Map(("html", 7), ("php", 8))
    val newset = set ++ set2
    println(newset) //Set(1, 5, 2, 3, 7, 4, 8)=
    val newmap = map ++ map2
    println(newmap)//Map(java -> 1, hadoop -> 3, hive -> 4, php -> 8, scala -> 2, html -> 7)

    //  col - ele			将元素从集合中删除			Set、Map、ArrayBuffer
    import scala.collection.mutable.ArrayBuffer
    var ab = new ArrayBuffer[Int]()
    val newab = ab += (1, 2, 3)
    println(newab)//  ArrayBuffer(1, 2, 3)
    println(set - 1) // Set(5, 2, 3, 4)
    println(map - "java")    //Map(scala -> 2, hadoop -> 3, hive -> 4)

    //  col - (ele1, ele2)	将子集合从集合中删除		Set、Map、ArrayBuffer
    val delset = newset - (7, 8)
    val delmap = newmap - ("html", "php")
    val delab = newab - (1, 2, 3)
    println(delset)  //Set(1, 5, 2, 3, 4)
    println(delmap) //Map(java -> 1, hadoop -> 3, hive -> 4, scala -> 2)
    println(delab)  //  ArrayBuffer()

    //  col1 ++ col2		将其他集合添加到集合尾部	Iterable
    val iter = Iterable(10,20,30,40,50)
    val iter1 = Iterable(11,12,13)
    println(iter ++ iter1)  //List(10, 20, 30, 40, 50, 11, 12, 13)
    println("--------------iterable------------------------------")

    //  col2 ++: col1		将其他集合添加到集合头部	Iterable
    println(iter1 ++: iter)  //List(11, 12, 13, 10, 20, 30, 40, 50)

    //  ele :: list			将元素添加到list的头部		List
    val list = List(1, 2, 3, 4, 5)
    val l = 6 :: list
    println(l)     //List(6, 1, 2, 3, 4, 5)

    //  list2 ::: list1		将其他list添加到list的头部		List
    val list2 = List(7, 8)
    val l2 = list ::: list2
    println(l2)    //List(1, 2, 3, 4, 5, 7, 8)

    //  list1 ::: list2		将其他list添加到list的尾部		List
    val l3 = list2 ::: list
    println(l3)   //List(7, 8, 1, 2, 3, 4, 5)

    //  set1 | set2			取两个set的并集			Set
    val s1 = Set(1, 2, 3, 4)
    val s2 = Set(3, 4, 5, 6)
    //    val su = s1 union  s2
    val su = s1 | s2
    println(su)   //Set(5, 1, 6, 2, 3, 4)

    //  set1 & set2			取两个set的交集			Set
    //    val si = s1 intersect s2
    val si = s1 & s2
    println(si)   //    Set(3, 4)

    //  set1 &~ set2		取两个set的diff(差集)				Set
    //    val sd = s1 diff s2
    val sd = s1 &~ s2
    println(sd)   // Set(1, 2) ,结果是&~前面的set集合的差
             println("******************输出************************")
    //  col += ele			给集合添加一个元素			可变集合
    val lt1 = scala.collection.mutable.Set(2, 3, 4, 5)
    val lt3 = lt1 += 8
    println(lt3) //Set(5, 2, 3, 4, 8)

    //  col += (ele1, ele2)	给集合添加一个集合			可变集合
    val lt2 = scala.collection.mutable.Set(10, 11, 12)
    val lt6 = scala.collection.mutable.Set(15, 16)
    //    println(lt4)
    //  col ++= col2		给集合添加一个集合			可变集合
    val lis = (lt2 ++= lt6)
    println(lis)             //Set(15, 12, 16, 10, 11)
    //  col -= ele			从集合中删除一个元素		可变集合

    val lis1 = (lt2 -= 10)
    println(lis1)      //Set(15, 12, 16, 11)

    //  col -= (ele1, ele2)	从集合中删除一个子集合		可变集合
    val ct = scala.collection.mutable.Set(10, 11, 12)
    println(ct -= (10,11))  //Set(12)
    //  col —= col2			从集合中删除一个子集合		可变集合
    val lst1 = scala.collection.mutable.Set(2, 3, 4, 5)
    val lst2 = scala.collection.mutable.Set(2, 3)
//    lst1 -= lst2
    //  ele +=: col			向集合头部添加一个元素		ArrayBuffer
    val arr = ArrayBuffer(2, 3, 4)
    println(1 +=:arr )   //ArrayBuffer(1, 2, 3, 4)
    //  col2 ++=: col		向集合头部添加一个集合		ArrayBuffer
    val ints = ArrayBuffer(1,2,3)
    println(ints ++=: arr)  //ArrayBuffer(1, 2, 3, 1, 2, 3, 4)
  }
}
