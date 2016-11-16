package java;

import java.util.List;
import java.util.stream.Collectors;

public class ListOperations {
    public List<String> mapToOrderURLs(List<Integer> orderIds){
        List<String> refList = orderIds.stream()
                .map(id -> "v1/orders/"+id)
                .collect(Collectors.toList());
        return refList;
    }
}
