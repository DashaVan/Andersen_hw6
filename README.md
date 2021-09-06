# Andersen_hw6
ДЗ №6

Продолжаем развивать маленькое приложение по контактам из предыдущего занятия.

Необходимо реализовать список, сделанный на стандартном контейнере, с помощью RecyclerView. Количество элементов в списке должно быть более 100. К информации по контакту необходимо добавить еще и картинку. Картинки можно брать с ресурса https://picsum.photos/, важно, чтобы у каждого контакта картинки были разные. Логику с переходом на детали необходимо сохранить.

Усложнение.
Для экрана со списком необходимо добавить возможность поиска по фамилии и имени. 

Так же необходимо добавить удаление контакта из списка. С помощью длинного нажатия на элемент, необходимо отобразить AlertDialog или же PopUp с опцией удаления. При нажатии на удалить, контакт должен пропадать из списка. 

Дополнительное усложнение.
Все изменения данных в адаптере необходимо проводить с помощью DiffUtil (https://developer.android.com/reference/androidx/recyclerview/widget/DiffUtil).

Дополнительное задание.
Необходимо добавить разделитель между элементами и отступы, с помощью ItemDecoration (https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.ItemDecoration).
