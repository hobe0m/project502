var commonLib = commonLib || {};

/**
* ajax 요청, 응답 편의 함수
*
* @param method : 요청 방식 - GET, POST, PUT, PATCH, DELETE ...
* @param url : 요청 URL
* @param params : 요청 데이터(POST, PUT, PATCH ... )
* @param responseType : json 값일경우 javascript 객체로 변환
*/

commonLib.ajaxLoad = function(method, url, params, responseType) {
    method = method || "GET";
    params = params || null;

    const token = document.querySelector("meta[name='_csrf']").content;
    const tokenHeader = document.querySelector("meta[name='_csrf_header']").content;


    // resolve는 성공 시 데이터, reject는 실패 시 데이터
    return new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest();

        xhr.open(method, url);
        xhr.setRequestHeader(tokenHeader, token);

        xhr.send(params); // 요청 body에 실릴 데이터, 키 = 값 & 키 = 값 형태로 쓰이고, FormData 객체(POST, PATCH, PUT)

        xhr.onreadystatechange = function() {
            if(xhr.status == 200 && xhr.readyState == XMLHttpRequest.DONE) {
                const resData = (responseType && responseType.toLowerCase() == 'json') ?
                    JSON.parse(xhr.responseText) : xhr.responseText;

                resolve(resData); // 성공 시 응답 데이터
            }
        };

        xhr.onabort = function(err) {
            reject(err); // 중단 시
        };

        xhr.onerror = function(err) {
            reject(err); // 요청 또는 응답 시 오류 발생
        }
    });
};