import java.util.ArrayList;
import java.util.List;

public class TreeInterpreter {
    public static boolean readTree(List<Integer> output, List<String> rules){
        List<String> tree=new ArrayList<>();
        for (int i=0;i< output.size();i++) {
            Integer state=output.get(i);
            tree.add(rules.get(state));

        }
        System.out.println(tree);
        return true;
    }
}
