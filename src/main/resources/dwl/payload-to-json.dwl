%dw 2.0
output application/json
---
payload map(payload01,index) -> {
	data: payload01
}