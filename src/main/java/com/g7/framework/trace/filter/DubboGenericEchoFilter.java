package com.g7.framework.trace.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcResult;
import com.alibaba.dubbo.rpc.support.ProtocolUtils;

/**
 * @author dreamyao
 * @title 泛化回声测试，用于dubbo监控服务可用性
 * @date 2018/9/2 下午1:46
 * @since 1.0.0
 */
@Activate(group = Constants.PROVIDER, order = -1)
public class DubboGenericEchoFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        //首先判断是$invoke泛化调用
        if (invocation.getMethodName().equals(Constants.$INVOKE)
                && invocation.getArguments() != null
                && invocation.getArguments().length == 3
                && !ProtocolUtils.isGeneric(invoker.getUrl().getParameter(Constants.GENERIC_KEY))) {

            //其次判断是$echo回声测试调用
            Object[] arguments = invocation.getArguments();
            Object realMethod = arguments[0];
            String[] argsTypes = (String[]) arguments[1];
            Object[] args = (Object[]) arguments[2];
            if (Constants.$ECHO.equals(realMethod)
                    && argsTypes != null
                    && argsTypes.length == 1
                    && args != null
                    && args.length == 1) {
                //到此处则证明属于泛化调用的回声调用
                return new RpcResult(args[0]);
            }
        }
        return invoker.invoke(invocation);
    }
}
