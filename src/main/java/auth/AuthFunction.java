package auth;

import io.micronaut.oci.core.TenancyIdProvider;
import io.micronaut.oci.function.OciFunction;

import javax.inject.*;
import java.util.List;
import java.util.stream.Collectors;

    @Singleton
    public class AuthFunction extends OciFunction {

        @Inject
        TenancyIdProvider tenantIdProvider;

        public List<String> handleRequest() {
            GetNamespaceRequest getNamespaceRequest = GetNamespaceRequest.builder()
                    .compartmentId(tenantIdProvider.getTenancyId()).build();
            String namespace = objectStorageClient.getNamespace(getNamespaceRequest).getValue();
            final ListBucketsRequest.Builder builder = ListBucketsRequest.builder();
            builder.namespaceName(namespace);
            builder.compartmentId(tenantIdProvider.getTenancyId());
            return objectStorageClient.listBuckets(builder.build())
                    .getItems().stream().map(BucketSummary::getName)
                    .collect(Collectors.toList());
        }

    }

}
