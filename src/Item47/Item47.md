#Prefer collection to stream as a return type - تفضيل إستخدام "المجوموعة (مكتبة جافا)" عن "ستريم" كبيانات مرجعية

**التلخيص:**

عند كتابة دالة تقوم بإرجاع متسلسلة من العناصر كقيمة مرجعية، يجب التنبه إلى :

- بعض المستخدمين يريدون الوصول إلى هذه المتسلسلة كـ "استريم" للعمل عليها.
- والبعض الآخر يريد أن يقوم بالمرور "Iterate" على العناصر كلٍ على حدى 

لذلك علينا محاولة احتواء الطرفين.

لو كان أنسب كون البيانات المُرجعة أن تكون "مجموعة" فنعما هي.

إذا لم يكن مناسبا أم تكون البيانات المٌرجعة "مجموع" قم بإرجاعها كـ "استريم" أو متسلسلة.

وفي المستقبل إذا قامت لغة "الجافا" بجعل الـ "استريم" يدعم المتسلسلة "Iterable"

إذا من الممكن أن تقوم بإرجاعها كـ "استريم" إذ أنه سيكون أفضل.

--------------------------------------------------------------

**الشرح**

قبل *جافا* 8 كان هناك أنواع يٌمكن إرجاعها بدون تردد أو تخوف من شيء ما، مثل: 

Collection, Set and List;

لكن بعد إضافة الـ "استريم" في *جافا*8 ربما في معظم الحالات يمكن إرجاع الـ "استريم" كبيانات مٌرجعة، وكما تعلم أن الـ "استريم" لا ترث التسلسلية حيث أنه قد عفى عليها الزمن.
لكن كتابة كود جيد يطلب دمج الإثنين معاً بحذر.

حالياً لا يوجد طريقة جيدة للتعامل مع هذا الأمر.

الكود الآتي لن يعمل بسبب محدودية استنتاجية الأنواع في لغة *الجافا*
```java
for (ProcessHandle ph : ProcessHandle.allProcesses()::iterator)
```
                                                                                                               

ولكي يعمل هذا الكود يجب أن نقوم بنظم المرجعة لهذه الدالة كالتالي:

```java
for (ProcessHandle ph : (Iterable<ProcessHandle>) ProcessHandle.allProcesses()::iterator)
```

هذا الكود سيعمل ولكنه مبهم وغير شفاف بالمرة، وهناك طريق أفضل يمكننا استخدامها؛ وهو عن طريق كتابة دالة مشتركة\محولة كالتالي:

```java
public static <E> Iterable<E> iterableOf(Stream<E> stream){
    return stream::iterator;
}

// الاستخدام كالتالي:

for (ProcessHandle p : iterableOf(ProcessHandle.allProcesses())){
    // استخدمها كما شئت
}
```


والعكس بالعكس، للفريق الذي يريد النوع الآخر من البيانات المٌرجعة (ك "استريم") يمكننا كتابة دالة مشتركة\محولة كالتالي:

```java
public static <E> Stream<E> streamOf(Iterable<E> iterable){
    return StreamSupport.stream(iterable.spliterator(), false);
}
```