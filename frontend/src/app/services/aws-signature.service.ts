import { Injectable } from '@angular/core';
import { SignatureV4 } from '@aws-sdk/signature-v4';
import { Sha256 } from '@aws-crypto/sha256-browser';
import { Credentials } from '@aws-sdk/types';

@Injectable({
    providedIn: 'root',
})
export class AwsSignatureService {
    private readonly region = 'us-east-1'; // Adjust to your setup
    private readonly serviceName = 's3';
    private readonly credentials: Credentials = {
        accessKeyId: 'minioadmin', // Your MinIO access key
        secretAccessKey: 'minioadmin', // Your MinIO secret key
    };

    constructor() {}

    async signRequest(
        method: string,
        url: string,
        headers: Record<string, string>
    ): Promise<Record<string, string>> {
        const signer = new SignatureV4({
            region: this.region,
            service: this.serviceName,
            credentials: this.credentials,
            sha256: Sha256,
        });

        const signedRequest = await signer.sign({
            method,
            protocol: 'http:',
            hostname: url.replace('http://', '').split('/')[0],
            path: '/' + url.split('/').slice(3).join('/'),
            headers,
        });

        return signedRequest.headers;
    }
}
