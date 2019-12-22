ALTER TABLE review add constraint fkey_product_review foreign key (product_id) references product (id);

ALTER TABLE comment add constraint fkey_comment_review foreign key (review_id) references review (id);