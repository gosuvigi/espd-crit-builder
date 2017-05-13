/**
 * Created by vigi on 5/13/2017 5:53 PM.
 */
export function get(url, params = {}) {
    const query = Object.keys(params)
        .map(k => encodeURIComponent(k) + '=' + encodeURIComponent(params[k]))
        .join('&');
    return fetch(url + '?' + query, {
        method: 'get',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        credentials: 'same-origin'
    });
}

export function post(url, params = {}) {
    return fetch(url, {
        method: 'post',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Access-Control-Expose-Headers': 'Location',
        },
        body: JSON.stringify(params),
        credentials: 'same-origin'
    });
}

export function put(url, params = {}) {
    return fetch(url, {
        method: 'put',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(params),
        credentials: 'same-origin'
    });
}
