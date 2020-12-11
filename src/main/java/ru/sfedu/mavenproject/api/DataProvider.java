package ru.sfedu.mavenproject.api;

import ru.sfedu.mavenproject.bean.Book;

import java.util.List;

public interface DataProvider {

    //Author

     <T extends Book> Object getBookByID(Class cl, long id);

//public Alter_book (){}

//public Set_title (){}

//public Attach_file (){}

//public Set_number_of_pages (){}

//public Make_order (){}

//public Set_order_date (){}

//public Set_cover_type (){}

//public Set_number_of_copies (){}

//public Calculate_cost (){}

//public Calculate_editor_work_cost (){}

//public Calculate_printing_cost (){}

//public Calculate_cover_cost (){}

//public Take_away_order (){}

//public Get_list_of_corrections (){}

//public Agreement_correction (){}

//public Agreement_edits (){}

//public Add_comment (){}

//public Agreement_meeting (){}

//public Add_author (){}

////Editor

//public Edit_book (){}

//public Get_Book (){}

//public Return_to_author (){}

//public Upload_editing_book_ (){}

//public Upload_book_for_printing (){}

//public End_editing (){}

//public Send_corrections_to_author (){}

//public Add_information_about_edits (){}

//public Make_meeting (){}

//public Return_to_author (){}

////Maker

//public Return_to_editor (){}

//public Take_for_printing (){}

//public Mark_as_finished (){}

////Chief

//public Count_published_books (){}

//public Count_printing_books (){}

//public Count_editing_books (){}

////Admin

//public Set_parameters (){}

//public Change_page_cost (){}

//public Change_work_cost (){}

//public Change_cover_cost (){}

//




}
