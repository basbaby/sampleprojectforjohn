%dw 2.0
output application/json
---
{
	"ErrorMessage": error.cause.message,
	"ErrorType": error.errorType.identifier,
	"Error Message": error.cause.message,
	"Error Code": message.attributes.statusCode
}
