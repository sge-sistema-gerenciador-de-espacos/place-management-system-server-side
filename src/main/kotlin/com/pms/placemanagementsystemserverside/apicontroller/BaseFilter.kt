package com.pms.placemanagementsystemserverside.apicontroller

//@Component
//class BaseFilter : Filter {
//    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
//
//        // Authorize (allow) all domains to consume the content
//        (response as HttpServletResponse).addHeader("Access-Control-Allow-Origin", "*")
//        response.addHeader("Access-Control-Allow-Headers", "Content-Type")
//        response.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST")
//
////        val resp = response
////        val req = request as HttpServletRequest
//
////        // For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
////        if (req?.method.equals("OPTIONS")) {
////            resp.status = HttpServletResponse.SC_ACCEPTED
////            return
////        }
//
////        print("Request: ${resp.getHeader("")}, \n" +
////                "Response: ${req.method}")
//
//        chain?.doFilter(request, response)
//    }
//}