%dw 2.0
output application/json
---

"Reconsumed Message from Offset " ++ (attributes.offset default "")
 ++ " for the Partition " ++ (attributes.partition default "")
