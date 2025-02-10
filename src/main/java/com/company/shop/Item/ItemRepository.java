package com.company.shop.Item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Slice<Item> findPageBy(Pageable page);
    // 전체 페이지 갯수 필요없으면
//    Slice<Item> findPageBy(Pageable page);

}
