package ru.skillbox.blog.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillbox.blog.repository.PostVotesRepository;
import ru.skillbox.blog.service.PostVotesService;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Service
public class PostVotesServiceImpl implements PostVotesService {

    @Autowired
    private PostVotesRepository postVotesRepository;

    @Override
    public Map<Integer,String> findStatistics() {
        final List<Object[]> statLDCs = postVotesRepository.statLDC();
        Map<Integer,String> mapLDC=new HashMap<>();
        for (Object[] statLDC : statLDCs) {
            String sss=String.join(":", Arrays.stream(statLDC).map(Object::toString).toArray(String[]::new));
            mapLDC.put(Integer.parseInt(statLDC[0].toString()),sss);
        }
        return mapLDC;
    }
}
