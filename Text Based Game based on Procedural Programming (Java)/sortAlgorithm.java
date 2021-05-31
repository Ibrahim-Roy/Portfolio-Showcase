class sortAlgorithm
{
    public static void main (String[] p)
    {
        sortAlgorithm();
        System.exit(0);
    }

    public static void sortAlgorithm()
    {
        int[] unsortedArray = {54,18,73,12,1,59,77,20,26,9,7,36,27,44,56,2,94,91,30,25,86,36,17,52,80,62,86,82,30,95,79,54,19,15,13,38,29,83,26,97,66,66,22,97,49,54,99,81,24,82,16,85,76,8,57,53,65,48,87,27,50,19,60,12,68,44,78,92,48,70,85,55,94,79,43,38,79,86,8,27,92,78,92,78,3,48,51,49,56,65,84,93,55,70,77,55,23,18,27,43};
        for (int i=(unsortedArray.length - 1); i>0; i--)
        {
            for (int j=0; j<i; j++)
            {
                if (unsortedArray[j]>unsortedArray[j+1])
                {
                    int temporary = unsortedArray[j];
                    unsortedArray[j] = unsortedArray[j+1];
                    unsortedArray[j+1] = temporary;
                }
            }
        }
        for (int i=0; i<unsortedArray.length; i++)
        {
            System.out.println(unsortedArray[i]);
        }
    }
}
