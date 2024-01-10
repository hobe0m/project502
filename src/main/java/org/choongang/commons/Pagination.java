package org.choongang.commons;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
@Data
public class Pagination {

    private int page;
    private int total;
    private int ranges;
    private int limit;

    private int firstRangePage; // 구간 별 첫 페이지
    private int lastRangePage; // 구간 별 마지막 페이지

    private int prevRangePage; // 이전 구간 첫 페이지 번호
    private int nextRangePage; // 다음 구간 첫 페이지 번호

    private int totalPages; // 전체 페이지 갯수
    private String baseURL; // 페이징 쿼리스트링 기본 URL

    /**
     *
     * @param page : 현재 페이지
     * @param total : 전체 레코드 갯수(총 페이지, 현재 페이지가 이디 인 지)
     * @param ranges : 페이지 구간 갯수
     * @param limit : 1페이지 당 레코드 갯수
     */
    public Pagination(int page, int total, int ranges, int limit, HttpServletRequest request) {

        page = Utils.onlyPositiveNumber(page, 1); // 음수 이하 일 때 양수의 기본값(1) 넣기, Utils에서 정의
        total = Utils.onlyPositiveNumber(total, 0); // 음수 이하 일 때 기본값(0, total은 없을 수도 있으니) 넣기
        ranges = Utils.onlyPositiveNumber(ranges, 10); // 음수 이하 일 때 기본값(10) 넣기, 페이지 구간 객수
        limit = Utils.onlyPositiveNumber(limit, 20); // 음수 이하 일 때 기본값(20) 넣기, 1페이지 구간 당 페이지 수

        // 전체 페이지 갯수
        int totalPages = (int)Math.ceil(total / (double)limit);

        // 구간 번호
        int rangeCnt = (page - 1) / ranges;

        // 구간 별 첫 페이지 구하기(2번째 구간의 첫 페이지면 6)
        int firstRangePage = rangeCnt * ranges + 1;
        
        // 구간 별 마지막 페이지 구하기
        int lastRangePage = firstRangePage + ranges -1;

        lastRangePage = lastRangePage > totalPages ? totalPages : lastRangePage;

        // 이전 구간 첫 페이지
        if(rangeCnt > 0) {
            prevRangePage = firstRangePage - ranges;
        }

        // 다음 구간 첫 페이지
        // 마지막 구간 번호
        int lastRangeCnt = (totalPages - 1) / ranges;
        if (rangeCnt < lastRangeCnt) { // 마지막 구간이 아닌 경우 다음 구간 첫 페이지 계산
                nextRangePage = firstRangePage + ranges;
        }

        /**
         * 쿼리스트링 값 유지 처리 = 쿼리스트링 값 중에서 page만 제외하고 다시 조합
         *  예) ?orderStatus=CASH&name=...&page=2 -> ?orderStatus=CASH&name=...
         *      ?page=2 -> ?
         *      없는 경우 -> ?
         *      
         *      &로 문자열 분리
         *      { "orderStatus=CASH", "name=....", "page=2" }
         */
        String baseURL = "?";
        if (request != null) {
            String queryString = request.getQueryString();
            if (StringUtils.hasText(queryString)) {
                queryString = queryString.replace("?", "");

                baseURL += Arrays.stream(queryString.split("&"))
                        .filter(s -> !s.contains("page="))
                        .collect(Collectors.joining("&"));

                baseURL = baseURL.length() > 1 ? baseURL += "&" : baseURL;
            }
        }

        this.page = page;
        this.total = total;
        this.ranges = ranges;
        this.limit = limit;
        this.firstRangePage = firstRangePage;
        this.lastRangePage = lastRangePage;
        this.totalPages = totalPages;
        this.baseURL = baseURL;
    }

    public Pagination(int page, int total, int ranges, int limit) {
        this(page, total, ranges, limit, null);
    }

    public List<String[]> getPages() {
        // 0 : 페이지 번호, 1 : 페이지 URL - ?page=페이지 번호

        return IntStream.rangeClosed(firstRangePage, lastRangePage)
                .mapToObj(p -> new String[] { String.valueOf(p), baseURL + "page=" + p})
                .toList();

    }
}














