package exercise.android.reemh.todo_items;

import junit.framework.TestCase;

import org.junit.Test;

public class TodoItemsHolderImplTest extends TestCase {

  @Test
  public void test_when_addingTodoItem_then_callingListShouldHaveThisItem(){
    // setup
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    assertEquals(0, holderUnderTest.getCurrentItems().size());

    // test
    holderUnderTest.addNewInProgressItem("do shopping");

    // verify
    assertEquals(1, holderUnderTest.getCurrentItems().size());
  }

  @Test
  public void  test_when_addingTwoItems_and_markingFirstItem_then_firstOneShouldMove(){
    TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
    assertEquals(0, holderUnderTest.getCurrentItems().size());
//    TodoItem item1 = new TodoItem("hi");
//    TodoItem item2 = new TodoItem("hello");
    holderUnderTest.addNewInProgressItem("hi");
    holderUnderTest.addNewInProgressItem("hello");
    assertEquals(2, holderUnderTest.getCurrentItems().size());

    holderUnderTest.markItemDone(holderUnderTest.getCurrentItems().get(0));
    assertEquals("hello", holderUnderTest.getCurrentItems().get(1).getTodo());
  }
  // TODO: add at least 10 more tests to verify correct behavior of your implementation of `TodoItemsHolderImpl` class
}