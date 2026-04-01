import http from 'k6/http';
import { check } from 'k6';

export let options ={
    vus: 50, // usuários simultâneos
    duration: '30s'
}

export default function(){
    let payload = JSON.stringify({
        type: "CLICK",
        userId: "user-123",
        timestamp: "2025-01-01T10:00:00"
    });

    let res = http.post('http://localhost:8080/events', payload, {
        headers:{'Content-Type': 'application/json'},
    })

    check(res, {'status 201': (r) => r.status === 201});
}