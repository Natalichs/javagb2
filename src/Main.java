import hw2.MyArrayDataException;
import hw2.MyArraySizeException;


public class Main {

    public static void main(String[] args) {
        int sizeArray1 = 4;
        int sizeArray2 = 4;
        String[][] array1 = {
                {"25","78","5","45"},
                {"14","45","7","8"},
                {"231","1","2","99"},
                {"47","77","e90","10"}};
        try{
            checkSize(sizeArray1,sizeArray2,array1);
            System.out.println("Сумма элементов массива = "+ sumStringArray(array1));

        }catch(MyArraySizeException e){
            e.printStackTrace();

        }catch (MyArrayDataException eo){
            eo.printStackTrace();
        }





    }
    public static void checkSize(int size1, int size2, String[][] array) {
        if (array.length != size1 ) {
            throw new MyArraySizeException("неверный размер массива");
        }
        else {
            for (int i = 0; i < size1; i++) {
                if(array[i].length != size2)
                    throw new MyArraySizeException("неверный размер массива");
            }
            System.out.println("размер массива подходящий");
        }
    }
    public static int sumStringArray(String[][] array){
        int l= array.length;
        int sum=0;
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < array[i].length; j++) {
                try {
                    sum+=Integer.parseInt(array[i][j]);
                }catch (NumberFormatException e){
                    throw new MyArrayDataException("невозможно преобразовать элемент массива array["+i+"]["+j+"]");
                }
            }
        }return sum;
        }
}
