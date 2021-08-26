package com.bizkicks.backend.repository;

public class ConsumptionRepository {
    
}

// 여기는 user와 join으로
// 조회, 추가

// user에 해당하는 사용자의 것을 추가해야 함.

// 추가하는 건 그냥 추가하면 됨.
// 조회하는 건, user에 해당하는 것들만 조회하고
// 그 조회한 consumption 중에서 좌표를 뽑아와야 함.

/*
1번.
user에 해당하는 consumption을 조회해서 id list를 가짐
consumption id로 coordinate 조회
=> N+1 문제

2번.
user에 해당하는 consumption, consumption에 해당하는 coordinate 조회
select *, count(*)
from consumption join fetch user join fetch coordinate
where depart_time > ~ and arrive_time < ~
order by depart_time asc
group by consumption_id

*/